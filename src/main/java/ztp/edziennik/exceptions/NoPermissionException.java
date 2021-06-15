package ztp.edziennik.exceptions;

public class NoPermissionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoPermissionException(String email){
        super("User " + email + " does not have permission to perform this operation");
    }
}
