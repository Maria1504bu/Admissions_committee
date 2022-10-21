package dao;

/**
 * wrap SQLIntegrityConstraintViolationException at insert and update methods to show that similar entity already exist in db
 * (unique value at db is reserved)
 */
public class AlreadyExistException extends Exception {
    public AlreadyExistException() {
    }

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
