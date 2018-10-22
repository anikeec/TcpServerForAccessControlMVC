package com.apu.TcpServerForAccessControlMVC;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
 
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
    @ExceptionHandler(IOException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex){
        logger.error("IOException Occured:: URL=" + request.getRequestURL());
        return "ioexception_error";
    }
    
//    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
//    public ResponseEntity<Object> handleAccessDeniedException(RuntimeException ex, WebRequest request) {
//        String error = null;
//        if(ex instanceof IllegalArgumentException)
//            error = "IllegalArgumentException Occured";
//        if(ex instanceof IllegalStateException)
//            error = "IllegalStateException Occured";
//        logger.error(error);
//        String bodyOfResponse = error;
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }
    
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    public ModelAndView handleAccessDeniedException(RuntimeException ex, WebRequest request) {
        String error = null;
        if(ex instanceof IllegalArgumentException)
            error = "IllegalArgumentException. " + ex.getMessage();
        if(ex instanceof IllegalStateException)
            error = "IllegalStateException. " + ex.getMessage();
        logger.error(error);
        String bodyOfResponse = error;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("message", error);
        return modelAndView;
    }
     
}
