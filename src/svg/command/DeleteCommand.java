package svg.command;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import svg.command.exceptions.NoSelectedNodeException;
import svg.figures.Figure;
import svg.figures.Union;

/**
 *
 * @author Ilya Shkuratov
 */
public class DeleteCommand implements Command{
    private Figure target;
    private DefaultMutableTreeNode child;
    private DefaultMutableTreeNode parent;
    private DefaultTreeModel model;
    
    public DeleteCommand(JTree tree) throws NoSelectedNodeException {
        try {
            child  = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        } catch (java.lang.NullPointerException ex) {
            throw new NoSelectedNodeException();
        }
        target = (Figure) child.getUserObject();
        parent = (DefaultMutableTreeNode) child.getParent();
        model = (DefaultTreeModel) tree.getModel();
    }

    @Override
    public void Execute() {
        model.removeNodeFromParent(child);
        ((Union) parent.getUserObject()).remove(target);
    }

    @Override
    public void Unexecute() {
        model.insertNodeInto(child, parent, parent.getChildCount());
        ((Union) parent.getUserObject()).add(target);
    }
    
}
