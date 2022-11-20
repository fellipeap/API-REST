package br.com.kyros.api.controllers.exceptions;

import br.com.kyros.api.services.exceptions.DataIntegrityViolationException;
import br.com.kyros.api.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceHandlerException {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardErrorException> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        StandardErrorException error = new StandardErrorException(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardErrorException>dataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                                 HttpServletRequest request) {
        StandardErrorException error = new StandardErrorException(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
