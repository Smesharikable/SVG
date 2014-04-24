package svg.figures;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 *
 * @author Ilya Shkuratov
 */
public class Rectangle implements Figure, Mementable, Serializable {
    
    private double x;
    private double y;
    private double height;
    private double width;
    private Rectangle2D rect;
    private String name;
    
    /**
     * 
     * @param rect
     * @param name
     */
    public Rectangle(Rectangle2D rect, String name) {
        this.rect = rect;
        this.name = name;
        this.x = rect.getX();
        this.y = rect.getY();
        this.height = rect.getHeight();
        this.width = rect.getWidth();
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param height
     * @param width
     * @param g  
     */
    public Rectangle(double x, double y, double height, double width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        rect = new Rectangle2D.Double(x, y, width, height);
        this.name = "rectangle";
    }
    
    /**
     * 
     * @param p
     * @param height
     * @param width
     * @param g  
     */
    public Rectangle(Point2D p, double height, double width) {
        this.x = p.getX();
        this.y = p.getY();
        this.height = height;
        this.width = width;
        rect = new Rectangle2D.Double(x, y, width, height);
        this.name = "rectangle";
    }
    
    /**
     * 
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return
     */
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    @Override
    public void Draw(Graphics2D g) {
        g.draw(rect);
    }
    
    @Override
    public void Move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        rect.setRect(x, y, width, height);
    }

    @Override
    public void Scale(double scale) {
        this.height *= scale;
        this.width *= scale;
        rect.setFrame(x, y, width, height);
    }

    @Override
    public Point2D getPosition() {
        return new Point2D.Double(x, y);
    }

    @Override
    public FigureState getMemento() {
        return new State();
    }

    @Override
    public void setMemento(FigureState memento) {
        memento.setState();
    }
    
    /**
     * 
     * @return
     */
    public FigureState getNameMemento() {
        return new NameState();
    }
    
    
    private class State implements FigureState {
        Rectangle2D srect = rect;
        private State() {}

        @Override
        public void setState() {
            rect = this.srect;
            x = rect.getX();
            y = rect.getY();
            width = rect.getWidth();
            height = rect.getHeight();
        }
    }
    
    private class NameState implements FigureState {
        String sname = name;
        private NameState() {}
        
        @Override
        public void setState() {
            name = sname;
        }
    }
    
}
