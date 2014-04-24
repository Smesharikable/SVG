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
public class ExcludeCommand implements Command{
    
    private JTree tree;
    private Union root;
    private DefaultMutableTreeNode treeRoot;
    private TreePath[] paths;
    private Figure[] objects;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode[] childs;
    private DefaultMutableTreeNode[] parents;
    
    public ExcludeCommand(JTree tree, Union root) throws NoSelectedNodeException {
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
        LinkedList<DefaultMutableTreeNode> q = new LinkedList();
        DefaultMutableTreeNode buff;
        DefaultMutableTreeNode curParent;
        for (int i = 0; i < paths.length; i ++) {
            childs[i] = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
            parents[i] = (DefaultMutableTreeNode) childs[i].getParent();
            objects[i] = (Figure) childs[i].getUserObject();
            // delete figure from union and from tree
            model.removeNodeFromParent(childs[i]);
            ((Union) parents[i].getUserObject()).remove(objects[i]);
        }
        // add figures to root union
        for (int i = 0; i < objects.length; i ++) {
            buff = new DefaultMutableTreeNode(objects[i]);
            model.insertNodeInto(buff, treeRoot, treeRoot.getChildCount());
            root.add(objects[i]);
            if (objects[i].getClass() == Union.class) q.addLast(buff);
        }
           
        while (!q.isEmpty()) {
            curParent = q.pollFirst();
            Object[] components = ((Union) curParent.getUserObject()).getComponents();
            for (int i = 0; i < components.length; i ++) {
                buff = new DefaultMutableTreeNode((Figure) components[i]);
                model.insertNodeInto(buff, curParent, curParent.getChildCount());
                TreePath path = new TreePath(buff.getPath());
                tree.makeVisible(path);
                if (components[i].getClass() == Union.class) q.addLast(buff);
            }
        }
    }

    @Override
    public void Unexecute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
