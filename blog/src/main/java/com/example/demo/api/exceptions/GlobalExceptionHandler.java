package com.example.demo.api.exceptions;

import com.example.demo.exceptions.*;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({NotFoundException.class, NoResultException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNotFoundException(Exception e, WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("Exception was thrown -> Return message: " + e.getMessage(), e);
        } else {
            log.error("Exception was thrown -> Return message: " + e.getMessage());
        }
        ExceptionResource exc = new ExceptionResource("Not Found", e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, exc, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({
        BadRequestException.class,
        BadFormatException.class,
        UnrecognizedPropertyException.class,
        WrongAction.class,
        PasswordWeakException.class,
        AlreadyExistingException.class,
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected @ResponseBody ResponseEntity<Object> handleInvalidRequest(
            Exception e, WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("Exception was thrown -> Return message: " + e.getMessage(), e);
        } else {
            log.error("Exception was thrown -> Return message: " + e.getMessage());
        }
        ExceptionResource exc = new ExceptionResource("Bad Request", e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, exc, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler({UnauthorizedUserException.class, NotAllowedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    protected ResponseEntity<Object> handleUnauthorized(Exception e, WebRequest request) {
        if (log.isDebugEnabled()) {
            log.error("Exception was thrown -> Return message: " + e.getMessage(), e);
        } else {
            log.error("Exception was thrown -> Return message: " + e.getMessage());
        }
        ExceptionResource exc = new ExceptionResource("Unauthorized exceptions", e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, exc, headers, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(Exception e, WebRequest request) {
        if (log.isDebugEnabled())
            log.error("Exception was thrown -> Retrun message: " + e.getMessage(), e);
        else log.error("Exception was thrown -> Retrun message: " + e.getMessage());
        ExceptionResource exceptionResource =
                new ExceptionResource("Access denied", e.getMessage());
        return handleExceptionInternal(
                e, exceptionResource, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
}
