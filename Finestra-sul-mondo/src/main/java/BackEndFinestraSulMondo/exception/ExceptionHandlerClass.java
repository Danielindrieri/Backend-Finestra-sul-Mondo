package BackEndFinestraSulMondo.exception;
import java.util.HashMap;
import java.util.Map;

import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage(e.getMessage());
        exceptionMessage.setStatus("400");
        exceptionMessage.setError("Bad Request");

        log.error("-----------------------------------------------------");
        log.error("IllegalArgumentException: {}", e.getMessage());
        log.error("-----------------------------------------------------");

        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionMessage> handleIllegalStateException(IllegalStateException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage(e.getMessage());
        exceptionMessage.setStatus("400");
        exceptionMessage.setError("Bad Request");

        log.error("-----------------------------------------------------");
        log.error("IllegalStateException: {}", e.getMessage());
        log.error("-----------------------------------------------------");

        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleUserNotFoundException(UsernameNotFoundException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage(e.getMessage());
        exceptionMessage.setStatus("404");
        exceptionMessage.setError("User Not Found");

        log.error("-----------------------------------------------------");
        log.error("UsernameNotFoundException: {}", e.getMessage());
        log.error("-----------------------------------------------------");

        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<ExceptionMessage> handleEntityNotFoundException(EntityNotFoundException e) {

        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage(e.getMessage());
        exceptionMessage.setStatus("404");
        exceptionMessage.setError("Not Found");

        log.error("-----------------------------------------------------");
        log.error("EntityNotFoundException: {}", e.getMessage());
        log.error("-----------------------------------------------------");

        return new ResponseEntity<ExceptionMessage>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EntityExistsException.class)
    protected ResponseEntity<ExceptionMessage> handleEntityExistsException(EntityExistsException e) {

        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage(e.getMessage());
        exceptionMessage.setStatus("409");
        exceptionMessage.setError("Conflict");

        log.error("-----------------------------------------------------");
        log.error("EntityExistsException: {}", e.getMessage());
        log.error("-----------------------------------------------------");

        return new ResponseEntity<ExceptionMessage>(exceptionMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionMessage> handleConsraintViolationException(ConstraintViolationException e,
                                                                              HttpServletRequest request) {

        Map<String, String> errors = new HashMap<String, String>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();

            if (fieldName.contains(".")) {
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
            }
            errors.put(fieldName, violation.getMessage());
        }

        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage("Validation failed");
        exceptionMessage.setStatus("400");
        exceptionMessage.setError(errors);

        log.error("-----------------------------------------------------");
        log.error("ConstraintViolationException: {}", e.getMessage());
        log.error("-----------------------------------------------------");

        return new ResponseEntity<ExceptionMessage>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<String> AccessDenied(AccessDeniedException e) {

        log.error("-----------------------------------------------------");
        log.error("AccessDeniedException: {}", e.getMessage());
        log.error("-----------------------------------------------------");

        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = SecurityException.class)
    protected ResponseEntity<String> entityNotFound(SecurityException e) {

        log.error("-----------------------------------------------------");
        log.error("SecurityException: {}", e.getMessage());
        log.error("-----------------------------------------------------");

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

