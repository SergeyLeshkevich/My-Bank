package by.leshkevich.utils.exceptions;

/**
 * @author S.Leshkevich
 * @version 1.0
 * class for creating new exceptions related to transaction
 */
public class TransactionException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message
     */
    public TransactionException(String message) {
        super(message);
    }

}
