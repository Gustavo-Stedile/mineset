package com.ifsp.teste.exceptions.handlers;

import com.ifsp.teste.exceptions.ExceptionResponse;
import com.ifsp.teste.exceptions.UnauthorizedAccessException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ModelAndView handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        ModelAndView modelAndView = new ModelAndView("erro-401");
        modelAndView.addObject("mensagem", ex.getMessage());
        return modelAndView;
    }
}