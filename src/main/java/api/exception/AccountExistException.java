package api.exception;

public class AccountExistException extends RuntimeException {
    public AccountExistException(String message) {
        super(message);
    }
}
