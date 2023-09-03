package by.leshkevich.utils.exceptions;

/**
 * @author S.Leshkevich
 * @version 1.0
 * class for creating new exceptions related to balance
 */
public class BalanceException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message
     */
    public BalanceException(String message) {
        super(message);
    }
}
