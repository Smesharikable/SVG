package svg.command;

import javax.swing.tree.DefaultMutableTreeNode;
import svg.figures.Figure;

/**
 *
 * @author Ilya Shkuratov
 */
public class ScaleCommand implements Command{
    
    private double scale;
    private final Figure f;
    
    /**
     * 
     * @param node 
     * @param scale  
     */
    public ScaleCommand(double scale, DefaultMutableTreeNode node) {
        this.f = (Figure) node.getUserObject();
        this.scale =  Math.abs(scale) < 0.1 ? 1 : Math.abs(scale);
    }
    
    @Override
    public void Execute() {
        f.Scale(scale);
    }

    @Override
    public void Unexecute() {
        f.Scale(1 / scale);
    }
    
}
