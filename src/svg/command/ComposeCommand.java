package svg.command;

import java.util.LinkedList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import svg.command.exceptions.NoSelectedNodeException;
import svg.figures.Figure;
import svg.figures.Union;

/**
 *
 * @author Ilya Shkuratov
 */
public class ComposeCommand implements Command{
    
    private JTree tree;
    private Union root;
    private DefaultMutableTreeNode treeRoot;
    private DefaultMutableTreeNode newNode;
    private TreePath[] paths;
    private Figure[] objects;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode[] childs;
    private DefaultMutableTreeNode[] parents;
    
    public ComposeCommand(JTree tree, Union root) throws NoSelectedNodeException {
        paths = tree.getSelectionPaths();
        if (paths == null) {
            throw new NoSelectedNodeException();
        }
        this.tree = tree;
        this.root = root;
        model = (DefaultTreeModel) tree.getModel();
        treeRoot = (DefaultMutableTreeNode) model.getRoot();
        childs = new DefaultMutableTreeNode[paths.length];
        parents = new DefaultMutableTreeNode[paths.length];
        objects = new Figure[paths.length];
    }
    
    @Override
    public void Execute() {
        for (int i = 0; i < paths.length; i ++) {
            childs[i] = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
            parents[i] = (DefaultMutableTreeNode) childs[i].getParent();
            objects[i] = (Figure) childs[i].getUserObject();
            // delete figure from union and from tree
            model.removeNodeFromParent(childs[i]);
            ((Union) parents[i].getUserObject()).remove(objects[i]);
        }
        Union u = new Union("Union", objects);
        root.add(u);
        newNode = new DefaultMutableTreeNode(u);
        model.insertNodeInto(newNode, treeRoot, treeRoot.getChildCount());
        
        DefaultMutableTreeNode buff;
        DefaultMutableTreeNode parent;
        LinkedList<DefaultMutableTreeNode> q = new LinkedList();
        for (int i = 0; i < objects.length; i ++) {
            buff = new DefaultMutableTreeNode(objects[i]);
            model.insertNodeInto(buff, newNode, newNode.getChildCount());
            if (objects[i].getClass() == Union.class) q.addLast(buff);              
        }
        
        while (!q.isEmpty()) {
            parent = q.pollFirst();
            Object[] components = ((Union) parent.getUserObject()).getComponents();
            for (int i = 0; i < components.length; i ++) {
                buff = new DefaultMutableTreeNode((Figure) components[i]);
                model.insertNodeInto(buff, parent, parent.getChildCount());
                if (components[i].getClass() == Union.class) q.addLast(buff);
            }
        }
        
        TreePath path = new TreePath(newNode.getPath());
        tree.makeVisible(path);
        tree.scrollPathToVisible(path);
    }

    @Override
    public void Unexecute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
