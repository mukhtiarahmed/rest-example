package com.hackerrank.assignment.exception;

import com.hackerrank.assignment.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static com.hackerrank.assignment.util.AssignmentHelper.FAILURE;

/**
 * The exception handler.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    /**
     * Handle EntityNotFoundException.
     *
     * @param exception the exception
     * @return the error response
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseDTO handleEntityNotFound(HttpServletRequest request, EntityNotFoundException exception) {
        log.info("EntityNotFoundException Occurred:: URL {} ", request.getRequestURL());
        return new ResponseDTO<String>(FAILURE, exception.getMessage(), null);

    }

    /**
     * Handle InvalidDataException.
     *
     * @param exception the exception
     * @return the error response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidDataException.class})
    public ResponseDTO handleBadRequest(HttpServletRequest request, InvalidDataException exception) {
        log.info("InvalidDataException Occurred:: URL {} ", request.getRequestURL());
        return new ResponseDTO<String>(FAILURE, exception.getMessage(), null);
    }

    /**
     * HttpRequestMethodNotSupportedException
     *
     * @param exception the exception
     * @return the error response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseDTO handleBadRequest(HttpServletRequest request,
                                        MethodArgumentTypeMismatchException exception) {
        log.info("MethodArgumentTypeMismatchException Occurred:: URL {} , method {} ",
                request.getRequestURL(), request.getMethod());
        return new ResponseDTO<String>(FAILURE, exception.getMessage(), null);
    }

    /**
     * HttpRequestMethodNotSupportedException
     *
     * @param exception the exception
     * @return the error response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseDTO handleBadRequest(HttpServletRequest request,
                                        HttpRequestMethodNotSupportedException exception) {
        log.info("HttpRequestMethodNotSupportedException Occurred:: URL {} , method {} ",
                request.getRequestURL(), request.getMethod());
        return new ResponseDTO<String>(FAILURE, exception.getMessage(), null);
    }

    /**
     * HttpRequestMethodNotSupportedException
     *
     * @param exception the exception
     * @return the error response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseDTO handleBadRequest(HttpServletRequest request, HttpMessageNotReadableException exception) {
        log.info("HttpMessageNotReadableException Occurred:: URL {} , method {} ",
                request.getRequestURL(), request.getMethod());
        return new ResponseDTO<String>(FAILURE, exception.getMessage(), null);
    }

    /**
     * MethodArgumentNotValidException
     *
     * @param exception the exception
     * @return the error response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException .class})
    public ResponseDTO handleBadRequest(HttpServletRequest request, MethodArgumentNotValidException exception) {
        log.info("MethodArgumentNotValidException Occurred:: URL {} , method {} ",
                request.getRequestURL(), request.getMethod());
        return new ResponseDTO<String>(FAILURE, exception.getMessage(), null);
    }

    /**
     * Handle AssignmentException.
     *
     * @param exception the exception
     * @return the error response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AssignmentException.class)
    public ResponseDTO handleBadRequest(HttpServletRequest request, AssignmentException exception) {
        log.error("AssignmentException Occurred:: URL {} , method {} ",
                request.getRequestURL(), request.getMethod(), exception);
        return new ResponseDTO<String>(FAILURE, exception.getMessage(), null);
    }

    /**
     * Handle all other runtime exceptions.
     *
     * @param exception the exception
     * @return the error response
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseDTO handleBadRequest(HttpServletRequest request, RuntimeException exception) {
        log.error("RuntimeException Occurred:: URL {} , method {} ",
                request.getRequestURL(), request.getMethod(), exception);
        return new ResponseDTO<String>(FAILURE, exception.getMessage(), null);
    }

    /**
     * Handle all SQLException exceptions.
     *
     * @param exception the exception
     * @return the error response
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public ResponseDTO handleBadRequest(HttpServletRequest request, SQLException exception) {
        log.error("SQLException Occurred:: URL {} , method {} ",
                request.getRequestURL(), request.getMethod(), exception);
        return new ResponseDTO<String>(FAILURE, exception.getMessage(), null);
    }

    /**
     * Handle all other exceptions.
     *
     * @param exception the exception
     * @return the error response
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseDTO handleBadRequest(HttpServletRequest request, Exception exception) {
        log.error("Exception Occurred:: URL {} , method {} ",
                request.getRequestURL(), request.getMethod(), exception);
        return new ResponseDTO<String>(FAILURE, exception.getMessage(), null);
    }

}
