package exception;

public class NegativeNumberArgumentException extends RuntimeException {
    public NegativeNumberArgumentException(String message) {
        super(message);
    }
}
