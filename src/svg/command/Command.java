package svg.command;

/**
 *
 * @author Ilya Shkuratov
 */
public interface Command {
    
    /**
     * 
     */
    public void Execute();
    /**
     * 
     */
    public void Unexecute();
    
}
