/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package svg.command.exceptions;

/**
 *
 * @author 1
 */
public class NoSelectedNodeException extends Exception {

    /**
     * Creates a new instance of
     * <code>NoSelectedNodeException</code> without detail message.
     */
    public NoSelectedNodeException() {
    }

    /**
     * Constructs an instance of
     * <code>NoSelectedNodeException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoSelectedNodeException(String msg) {
        super(msg);
    }
}
