package ztp.edziennik.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String email) {
        super("User with email= " + email + " not found");
    }
}
