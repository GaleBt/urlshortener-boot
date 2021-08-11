package org.spring101.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.spring101.urlshortener.exception.InvalidUriException;
import org.spring101.urlshortener.exception.UriNotFoundException;
import org.spring101.urlshortener.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Provides exception handling for controllers
 */
@ControllerAdvice()
public class ApiExceptionHandler {
    
    /**
     * JSON object mapper
     */
    @Autowired
    private ObjectMapper mapper;
    
    /**
     * Handles exception thrown when validation fails
     * 
     * @param e MethodArgumentNotValidException
     * @param response HttpServletResponse
     * 
     * @throws IOException 
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletResponse response
    ) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = new ErrorResponse();
        String[] messages = e.getBindingResult().getAllErrors().stream().map(ex -> ex.getDefaultMessage()).toArray(String[]::new);
        errorResponse.setError(String.join(",", messages));
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
    
    /**
     * Handles JSON deserialization errors
     * 
     * @param e HttpMessageNotReadableException
     * @param response HttpServletResponse
     * 
     * @throws IOException 
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e,
            HttpServletResponse response
    ) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Request is invalid");
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
    
    /**
     * Handles exceptions, when submitted URI is invalid 
     * 
     * @param e InvalidUriException
     * @param response HttpServletResponse
     * 
     * @throws IOException 
     */
    @ExceptionHandler(InvalidUriException.class)
    public void handleInvalidUriException(
            InvalidUriException e,
            HttpServletResponse response
    ) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(e.getMessage());
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
    
    /**
     * Handles exceptions, when URI is not found
     * 
     * @param e UriNotFoundException
     * @param response HttpServletResponse
     * 
     * @throws IOException 
     */
    @ExceptionHandler(UriNotFoundException.class)
    public void handleUriNotFoundException(
            UriNotFoundException e,
            HttpServletResponse response
    ) throws IOException {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(e.getMessage());
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
    
}
