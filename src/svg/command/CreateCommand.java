package svg.command;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import svg.command.exceptions.IncorrectFigureIndexException;
import svg.figures.*;

/**
 *
 * @author Ilya Shkuratov
 */
public class CreateCommand implements Command {
    private Figure f;
    private Union root;
    private DefaultMutableTreeNode parent;
    private DefaultMutableTreeNode newNode;
    private TreePath path;
    private DefaultTreeModel model;
    private JTree tree;
    
    public CreateCommand(int index, Shape s, String name, Graphics2D g,
            Union root, JTree tree) throws IncorrectFigureIndexException {
        switch (index) {
            case FiguresIndex.LINE:
                f = new Line((Line2D) s, name);
                break;
            case FiguresIndex.CIRCLE:
                f = new Circle((Ellipse2D) s, name);
                break;
            case FiguresIndex.RECTANGLE:
                f = new Rectangle((Rectangle2D) s, name);
                break;
            default:
                throw new svg.command.exceptions.IncorrectFigureIndexException();
        }
        this.root = root;
        this.tree = tree;
        this.model = (DefaultTreeModel) tree.getModel();
    }

    @Override
    public void Execute() {
        root.add(f);
        parent = (DefaultMutableTreeNode) model.getRoot();
        newNode = new DefaultMutableTreeNode(f);
        model.insertNodeInto(newNode, parent, parent.getChildCount());
        //newNode.setParent(parent);
        path = new TreePath(model.getPathToRoot(newNode));
        tree.makeVisible(path);
        tree.scrollPathToVisible(path);
    }

    @Override
    public void Unexecute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
