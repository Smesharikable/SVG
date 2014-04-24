/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package svg.command;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import svg.figures.Figure;

/**
 *
 * @author 1
 */
public class RenameCommand implements Command{
    
    private Figure target;
    private String oldName;
    private String newName;
    
    /**
     * 
     * @param target 
     * @param newName
     */
    public RenameCommand(Figure target, String newName) {
        this.target = target;
        this.newName = newName;
    }

    
    @Override
    public void Execute() {
        oldName = target.getName();
        target.setName(newName);
    }

    @Override
    public void Unexecute() {
        target.setName(oldName);
    }
    
}
