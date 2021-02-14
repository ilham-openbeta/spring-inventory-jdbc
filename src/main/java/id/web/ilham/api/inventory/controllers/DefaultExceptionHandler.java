package id.web.ilham.api.inventory.controllers;

import id.web.ilham.api.inventory.exceptions.ApplicationException;
import id.web.ilham.api.inventory.models.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private ResponseEntity<Object> handleBindingResult(BindingResult result, HttpStatus status) {
        Map<String, List<String>> errors = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            String name = fieldError.getField();
            String value = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            List<String> messages = errors.get(name);
            if (messages == null) {
                messages = new ArrayList<>();
                errors.put(name, messages);
            }
            messages.add(value);
        });

        String message = messageSource.getMessage("error." + status.value(), null,
                LocaleContextHolder.getLocale());

        ResponseMessage<Object> body = ResponseMessage.error(status.value(), message, errors);
        return ResponseEntity.ok(body);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleBindingResult(ex, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleBindingResult(ex.getBindingResult(), status);
    }

    private ResponseEntity<Object> handleException(HttpStatus status) {
        String message = "error." + status.value();
        return handleException(status, message);
    }

    private ResponseEntity<Object> handleException(HttpStatus status, String message) {
        String localizedMessage = messageSource.getMessage(message, null, message, LocaleContextHolder.getLocale());
        ResponseMessage<Object> body = ResponseMessage.error(status.value(), localizedMessage);
        return ResponseEntity.ok(body);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(status);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException e) {
        return handleException(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleBadCredentialsException(FileNotFoundException e) {
        return handleException(HttpStatus.NOT_FOUND, "File Not Found");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {
        return handleException(HttpStatus.BAD_REQUEST, "Incorrect username or password");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        return handleException(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Exception e) {
        logger.error("Unknown Exception", e);
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
