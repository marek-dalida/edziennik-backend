package ztp.edziennik.exceptions.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ztp.edziennik.exceptions.NoPermissionException;
import ztp.edziennik.exceptions.ObjectNotFoundException;
import ztp.edziennik.exceptions.UserNotFoundException;
import ztp.edziennik.exceptions.common.ErrorMessage;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ ObjectNotFoundException.class })
    public final ResponseEntity<ErrorMessage> handleObjectNotFoundException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorMessage, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ UserNotFoundException.class, NoPermissionException.class })
    public final ResponseEntity<ErrorMessage> handleAuthException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorMessage, headers, HttpStatus.FORBIDDEN);
    }
}
