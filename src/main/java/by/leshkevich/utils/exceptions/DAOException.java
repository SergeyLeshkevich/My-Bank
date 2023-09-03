package by.leshkevich.utils.exceptions;

/**
 * @author S.Leshkevich
 * @version 1.0
 * class to create new exceptions. Discarded when working in DAO
 */
public class DAOException extends Exception {
    /**
     * constructor  DAOException
     */
    public DAOException() {
    }

    /**
     * constructor  DAOException
     * @param message String message
     * @param cause Throwable cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor  DAOException
     * @param cause Throwable cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message message
     */
    public DAOException(String message) {
        super(message);
    }

}
