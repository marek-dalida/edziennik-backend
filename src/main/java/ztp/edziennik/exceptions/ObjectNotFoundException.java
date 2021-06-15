package ztp.edziennik.exceptions;

public class ObjectNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(Long id) {
        super( "Object with id= " + id + " not found");
    }
    public ObjectNotFoundException(Long id, String objectName ) {
        super( objectName + " with id= " + id + " not found");
    }
}
