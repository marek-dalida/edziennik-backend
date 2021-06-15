package ztp.edziennik.exceptions;

public class NotGroupMemberException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NotGroupMemberException(Long userId){
        super("User with id= " + userId + " is not a group member");
    }
}
