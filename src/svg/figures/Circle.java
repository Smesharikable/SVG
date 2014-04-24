package svg.figures;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 *
 * @author Ilya Shkuratov
 */
public class Circle implements Figure, Mementable, Serializable {
    
    private double x;
    private double y;
    private double radius;
    private Ellipse2D circle;
    private String name;
    
    /**
     * 
     * @param circle
     * @param name
     */
    public Circle(Ellipse2D circle, String name) {
        this.circle = circle;
        this.name = name;
        this.x = circle.getCenterX();
        this.y = circle.getCenterY();
        this.radius = circle.getHeight();
        this.circle = new Ellipse2D.Double(x - radius / 2, y - radius / 2, this.radius, this.radius);
    }
    
    /**
     * 
     * @param x 
     * @param y 
     * @param radius
     * @param g  
     */
    public Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        circle = new Ellipse2D.Double(x, y, this.radius, this.radius);
        this.name = "circle";
    }
    
    /**
     * 
     * @param p
     * @param radius
     * @param g  
     */
    public Circle(Point2D p, double radius) {
        this.x = p.getX();
        this.y = p.getY();
        this.radius = radius;
        circle = new Ellipse2D.Double(x, y, this.radius, this.radius);
        this.name = "circle";
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
        g.draw(circle);
    }
    
    @Override
    public void Move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        circle.setFrame(x - radius / 2, y - radius / 2, radius, radius);
    }

    @Override
    public void Scale(double scale) {
        this.radius *= scale;
        circle.setFrame(x - radius / 2, y - radius / 2, radius, radius);
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
        Ellipse2D scircle = circle;
        private State() {}

        @Override
        public void setState() {
            circle = this.scircle;
            x = circle.getX();
            y = circle.getY();
            radius = circle.getHeight();
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
