package com.cfm.AddressBook.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    private ResponseEntity<?> handleException(Throwable e, HttpServletRequest httpReq, HttpServletResponse httpRes) {
        log.error("Error Message: {}",e.getMessage());
        httpRes.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    private ResponseEntity<?> handleNotFoundException(ResourceNotFoundException e, HttpServletRequest httpReq, HttpServletResponse httpRes) {
        log.warn(e.getMessage());
        httpRes.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
    }
}
