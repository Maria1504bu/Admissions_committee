package dao;

/**
 * throw it if insert/delete/update methods change more than 1 row, or don`t change it at all
 * */
public class WrongExecutedQueryException extends Throwable {
    public WrongExecutedQueryException() {
    }

    public WrongExecutedQueryException(String message) {
        super(message);
    }

    public WrongExecutedQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
