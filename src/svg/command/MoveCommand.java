package svg.command;

import java.awt.geom.Point2D;
import javax.swing.tree.DefaultMutableTreeNode;
import svg.figures.Figure;

/**
 *
 * @author Ilya Shkuratov
 */
public class MoveCommand implements Command{
    
    private double dx;
    private double dy;
    private final Figure f;
    
    /**
     * 
     * @param dx
     * @param dy
     * @param model 
     * @param node  
     */
    public MoveCommand(double dx, double dy, DefaultMutableTreeNode node) {
        this.f = (Figure) node.getUserObject();
        this.dy = dy;
        this.dx = dx;
    }
    
    /**
     * 
     * @param p
     * @param model 
     * @param node  
     */
    public MoveCommand(Point2D p, DefaultMutableTreeNode node) {
        this.f = (Figure) node.getUserObject();
        Point2D fp = f.getPosition();
        dx = p.getX() - fp.getX();
        dy = p.getY() - fp.getY();
    }
    
    /**
     * 
     */
    @Override
    public void Execute() {
        f.Move(dx, dy);
    }
    
    /**
     * 
     */
    @Override
    public void Unexecute() {
        f.Move(-dx, -dy);
    }
}
