package svg.figures;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Ilya Shkuratov
 */
public class Union implements Figure, Serializable{
    
    private String name;
    private HashSet components;
    
    public Union() {
        components = new HashSet<Figure>();
        name = "Union";
    }
    
    public Union(String name) {
        components = new HashSet<Figure>();
        this.name = name;
    }
    
    public Union(Figure[] figures) {
        components = new HashSet<Figure>();
        components.addAll(Arrays.asList(figures));
        name = "Union";
    }
    
    public Union(String name, Figure[] figures) {
        components = new HashSet<Figure>();
        components.addAll(Arrays.asList(figures));
        this.name = name;
    }
    
    public boolean add(Figure f) {
        return components.add(f);
    }
    
    public void add(Figure[] figures) {
        components.addAll(Arrays.asList(figures));
    }
    
    public boolean add(Union u) {
        return components.add(u);
    }
    
    public boolean add(Union[] unions) {
        return components.addAll(Arrays.asList(unions));
    }
    
    public boolean remove(Figure f) {
        return components.remove(f);
    }
    
    public void remove(Figure[] figures) {
        components.remove(Arrays.asList(figures));
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
    
    public Object[] getComponents() {
        return components.toArray();
    }
    
    @Override
    public String toString(){
        return name;
    }

    @Override
    public void Draw(Graphics2D g) {
        for(Iterator iter = components.iterator(); iter.hasNext(); ){
           ((Figure) iter.next()).Draw(g);
        }
    }

    @Override
    public void Move(double dx, double dy) {
        for(Iterator iter = components.iterator(); iter.hasNext(); ){
           ((Figure) iter.next()).Move(dx, dy);
        }
    }

    @Override
    public void Scale(double scale) {
        for(Iterator iter = components.iterator(); iter.hasNext(); ){
           ((Figure) iter.next()).Scale(scale);
        }
    }

    @Override
    public Point2D getPosition() {
        return new Point2D.Double(0, 0);
    }
    
}
