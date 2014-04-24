package svg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.io.*;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import svg.command.*;
import svg.command.exceptions.IncorrectFigureIndexException;
import svg.command.exceptions.NoSelectedNodeException;
import svg.figures.Figure;
import svg.figures.Union;

/**
 *
 * @author Ilya Shkuratov
 */
public class SceneManager {
    
    private Union root;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;
    private Stack<Command> commandStack;
    private JFrame mainFrame;
    private JFileChooser fileDialog;
    
    private class DataFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return f.getName().toLowerCase().endsWith(".dat") || f.isDirectory();
        }

        @Override
        public String getDescription() {
            return "Data file";
        }
        
    }
    
    /**
     * 
     * @param mainFrame
     * @param treeModel
     * @param tree
     */
    protected SceneManager(JFrame mainFrame, JTree tree) {
        this.mainFrame = mainFrame;
        this.tree = tree;
        this.treeModel = (DefaultTreeModel) tree.getModel();
        this.rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
        this.root = (Union) rootNode.getUserObject();
        commandStack = new Stack();
        
        fileDialog = new JFileChooser();
        fileDialog.setCurrentDirectory(new File(".\\Data"));
        fileDialog.setFileFilter(new DataFilter());
    }
    
    protected void setRootNode(DefaultMutableTreeNode rootNode) {
        this.rootNode = rootNode;
        this.root = (Union) rootNode.getUserObject();
    }
    
    /**
     * 
     * @param g 
     */
    protected void drawFigures(Graphics2D g) {
        g.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLUE);
        root.Draw(g);
    }
    
    protected void createFigure(int index, Shape s, String name, Graphics2D g) {
        try {
            CreateCommand c = new CreateCommand(index, s, name, g, root, tree);
            c.Execute();
            commandStack.push(c);
            System.out.println(name);
        } catch (IncorrectFigureIndexException ex) {
            Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, "Incorrect index", ex);
        }
    }
    
    protected void deleteFigure() {
        try {
            DeleteCommand c = new DeleteCommand(tree);
            c.Execute();
            commandStack.push(c);
        } catch (NoSelectedNodeException ex) {
            Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, "Can't delete figure", ex);
        }
    }
    
    protected void moveFigure(double dx, double dy, DefaultMutableTreeNode node) {
        MoveCommand c = new MoveCommand(dx, dy, node);
        c.Execute();
        commandStack.push(c);
    }
    
    protected void scaleCommand(double scale, DefaultMutableTreeNode node) {
        ScaleCommand c = new ScaleCommand(scale, node);
        c.Execute();
        commandStack.push(c);
    }
    
    protected void composeFigures() {
        try {
            ComposeCommand c = new ComposeCommand(tree, root);
            c.Execute();
            commandStack.push(c);
        } catch (NoSelectedNodeException ex) {
            Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, "No items for compose", ex);
        }
    }
    
    protected void excludeFigures() {
        try {
            ExcludeCommand c = new ExcludeCommand(tree, root);
            c.Execute();
            commandStack.push(c);
        } catch (NoSelectedNodeException ex) {
            Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, "No items for exclude", ex);
        }
    }
    
    protected void renameFigure(Figure f, String name) {
        RenameCommand c = new RenameCommand(f, name);
        c.Execute();
        commandStack.push(c);
    }
    
    protected void saveScene() {
        int result = fileDialog.showSaveDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileDialog.getSelectedFile()));
                out.writeObject(root);
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, "Can't save file", ex);
            }
        }
    }
    
    protected void openScene() {
        int result = fileDialog.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileDialog.getSelectedFile()));
                Object u = in.readObject();
                in.close();
                if (u.getClass() == Union.class) {
                    root = (Union) u;
                } else 
                    return;
            } catch (IOException ex) {
                Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, "Can't open file", ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            commandStack.clear();
            
            // build new JTree
            LinkedList<DefaultMutableTreeNode> q = new LinkedList();
            DefaultMutableTreeNode buf;
            rootNode = new DefaultMutableTreeNode(root);
            treeModel.setRoot(rootNode);
            q.addLast(rootNode);
            while(!q.isEmpty()) {
                DefaultMutableTreeNode parent = q.pollFirst();
                Figure f = (Figure) parent.getUserObject();
                if (f.getClass() == Union.class) {
                    Object[] components = ((Union) f).getComponents();
                    for (int i = 0; i < components.length; i ++) {
                        buf = new DefaultMutableTreeNode(components[i]);
                        treeModel.insertNodeInto(buf, parent, parent.getChildCount());
                        if (components[i].getClass() == Union.class) {
                            q.addLast(buf);
                        } else {
                            TreePath path = new TreePath(buf.getPath());
                            tree.makeVisible(path);
                        }
                    }
                } else {
                    treeModel.insertNodeInto(new DefaultMutableTreeNode(f), parent, parent.getChildCount());
                }
            }
        }
    }
}
