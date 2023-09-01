package by.leshkevich.utils.exceptions;

public class BalanceException extends Exception{
    public BalanceException() {
    }

    public BalanceException(String message) {
        super(message);
    }

    public BalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BalanceException(Throwable cause) {
        super(cause);
    }
}
