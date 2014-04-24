package svg.figures;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 *
 * @author Ilya Shkuratov
 */
public class Line implements Figure, Mementable, Serializable {
    
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double X;
    private double Y;
    private Line2D line;
    private String name;
    
    
    /**
     * 
     * @param line
     * @param name
     * @param g
     */
    public Line(Line2D line, String name) {
        this.line = line;
        this.name = name;
        this.x1 = line.getX1();
        this.x2 = line.getX2();
        this.y1 = line.getY1();
        this.y2 = line.getY2();
        this.X = (x1 + x2) / 2;
        this.Y = (y1 + y2) / 2;
    }
    
    /**
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param g  
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.X = (x1 + x2) / 2;
        this.Y = (y1 + y2) / 2;
        line = new Line2D.Double(x1, y1, x2, y2);
        this.name = "line";
    }
    
    /**
     * 
     * @param p1
     * @param p2
     * @param g  
     */
    public Line(Point2D p1, Point2D p2) {
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
        this.X = (this.x1 + this.x2) / 2;
        this.Y = (this.y1 + this.y2) / 2;
        line = new Line2D.Double(x1, y1, x2, y2);
        this.name = "line";
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
    
    /**
     * 
     */
    @Override
    public void Draw(Graphics2D g) {
        g.draw(line);
    }
    
    @Override
    public void Move(double dx, double dy) {
        this.x1 += dx;
        this.x2 += dx;
        this.y1 += dy;
        this.y2 += dy;
        this.X += dx;
        this.Y += dy;
        line.setLine(x1, y1, x2, y2);
    }

    @Override
    public void Scale(double scale) {
        this.x1 = X + (x1 - x2) * scale / 2;
        this.x2 = X + (x2 - x1) * scale / 2;
        this.y1 = Y + (y1 - y2) * scale / 2;
        this.y2 = Y + (y2 - y1) * scale / 2;
        line.setLine(x1, y1, x2, y2);
    }

    @Override
    public Point2D getPosition() {
        return new Point2D.Double(X, Y);
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
        Line2D sline = line;
        private State() {}

        @Override
        public void setState() {
            line = this.sline;
            x1 = line.getX1();
            x2 = line.getX2();
            y1 = line.getY1();
            y2 = line.getY2();
            X = (x1 + x2) / 2;
            Y = (y1 + y2) / 2;
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
