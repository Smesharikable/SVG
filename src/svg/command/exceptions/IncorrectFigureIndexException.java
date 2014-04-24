package svg.command.exceptions;

/**
 *
 * @author Ilya Shkuratov
 */
public class IncorrectFigureIndexException extends Exception {

    /**
     * Creates a new instance of
     * <code>IncorrectFigureIndexException</code> without detail message.
     */
    public IncorrectFigureIndexException() {
    }

    /**
     * Constructs an instance of
     * <code>IncorrectFigureIndexException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public IncorrectFigureIndexException(String msg) {
        super(msg);
    }
}
