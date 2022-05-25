package ca.lebl.monitoring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EndpointNotFoundException.class)
    public ResponseEntity<Object> handleEndpointNotFoundException(
        EndpointNotFoundException e,
        WebRequest request
    ) {
        return notFoundResponse();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
        ConstraintViolationException e
    ) {
        // thank you https://medium.com/@aamine/customized-input-validation-in-spring-boot-1927aa440bc6
        String exceptionResponse = String.format("Invalid input parameters: %s\n", e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> notFoundResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "No endpoint exists for the given ID");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
