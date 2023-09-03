package by.leshkevich.utils.exceptions;

/**
 * @author S.Leshkevich
 * @version 1.0
 * class for creating new bank account related exceptions
 */
public class AccountException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message
     */
    public AccountException(String message) {
        super(message);
    }
}
