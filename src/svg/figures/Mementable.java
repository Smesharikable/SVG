package svg.figures;

/**
 *
 * @author Ilya Shkuratov
 */
public interface Mementable {
    
    /**
     * 
     * @return
     */
    public FigureState getMemento();
    /**
     * 
     * @param memento
     */
    public void setMemento(FigureState memento);
    
}
