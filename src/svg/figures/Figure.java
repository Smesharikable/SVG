package svg.figures;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Ilya Shkuratov
 */
public interface Figure {
    
    /**
     * 
     * @param g 
     */
    public void Draw(Graphics2D g);
    /**
     * 
     * @param dx
     * @param dy
     */
    public void Move(double dx, double dy);
    /**
     * 
     * @param scale
     */
    public void Scale(double scale);
    /**
     * 
     * @return
     */
    public Point2D getPosition();
    /**
     * 
     * @param name
     */
    public void setName(String name);
    /**
     * 
     * @return
     */
    public String getName();
}
