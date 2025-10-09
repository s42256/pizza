package cqu.pizza.lifecycle;

/**
 *
 * @author sisak
 */
public class ReportException extends RuntimeException {

    /**
     * Creates a new {@code ReportException} with a message.
     * @param errorMessage description of the problem
     */
    public ReportException(String errorMessage) {
        super(errorMessage);
    }
}