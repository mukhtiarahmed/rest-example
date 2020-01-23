package com.hackerrank.assignment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.exception.ConfigurationException;
import com.hackerrank.assignment.exception.EntityNotFoundException;
import com.hackerrank.assignment.exception.InitializationNotAllowedException;
import com.hackerrank.assignment.exception.InvalidDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.UUID;

/**
 * The Helper class.
 *
 * @author mukhtiar.ahmed
 */
@Slf4j
public final class AssignmentHelper {

    /**
     * private Constructor
     */
    private AssignmentHelper() throws InitializationNotAllowedException {
        throw new InitializationNotAllowedException("Initialization not allowed");
    }

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    /**
     * Api Version
     */
    public static final String API_VER = "/api/1.0";

    /**
     * The object mapper.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * <p>
     * Represents the error message.
     * </p>
     */
    public static final String ERROR_MESSAGE = "Internal Server Error";
    /**
     * <p>
     * Represents the error message.
     * </p>
     */
    public static final String INVALID_REQUEST_MESSAGE = "HTTP URL or Method is not valid";
    /**
     * <p>
     * Represents the error message.
     * </p>
     */
    public static final String MISSING_REQUEST_JSON_MESSAGE = "Missing Request Input JSON";

    /**
     * <p>
     * Represents the entrance message.
     * </p>
     */
    private static final String MESSAGE_ENTRANCE = "Entering method %1$s.";

    /**
     * <p>
     * Represents the exit message.
     * </p>
     */
    private static final String MESSAGE_EXIT = "Exiting method %1$s.";

    /**
     * <p>
     * Represents the error message.
     * </p>
     */
    private static final String MESSAGE_ERROR = "Error in method %1$s. Details:";

    private static final String MESSAGE_PROVIDED_ERROR = " %s is not provided";

    /**
     * It checks whether a given object is null.
     *
     * @param object the object to be checked
     * @param name   the name of the object, used in the exception message
     * @throws InvalidDataException the exception thrown when the object is null
     */
    public static void checkNull(Object object, String name) throws InvalidDataException {
        if (object == null) {
            throw new InvalidDataException(String.format(MESSAGE_PROVIDED_ERROR, name));
        }
    }

    /**
     * It checks whether a given string is null or empty.
     *
     * @param str  the string to be checked
     * @param name the name of the object, used in the exception message
     * @throws InvalidDataException the exception thrown when the object is null
     */
    public static void checkNullOrEmpty(String str, String name) throws InvalidDataException {
        if (str == null || str.trim().isEmpty()) {
            throw new InvalidDataException(String.format(MESSAGE_PROVIDED_ERROR, name));
        }
    }

    /**
     * It checks whether the given list is null or empty.
     *
     * @param list the list
     * @param name the name of the object, used in the exception message
     * @throws InvalidDataException the exception thrown when the list is null or empty
     */
    public static void checkNullOrEmpty(Collection<?> list, String name) throws InvalidDataException {
        if (list == null || list.isEmpty()) {
            throw new InvalidDataException(String.format(MESSAGE_PROVIDED_ERROR, name));
        }
    }

    /**
     * Check if the value is positive.
     *
     * @param value the value
     * @param name  the name
     * @throws InvalidDataException if the value is not positive
     */
    public static void checkPositive(long value, String name) throws InvalidDataException {
        if (value <= 0) {
            throw new InvalidDataException(name + " should be a positive value.");
        }
    }

    /**
     * Check if the configuration state is true.
     *
     * @param state   the state
     * @param message the error message if the state is not true
     * @throws ConfigurationException if the state is not true
     */
    public static void checkConfigState(boolean state, String message) {
        if (!state) {
            throw new ConfigurationException(message);
        }
    }

    /**
     * Check if the configuration is null or not.
     *
     * @param object the object
     * @param name   the name
     * @throws ConfigurationException if the state is not true
     */
    public static void checkConfigNotNull(Object object, String name) {
        if (object == null) {
            throw new ConfigurationException(String.format("%s should be provided", name));
        }
    }

    /**
     * <p>
     * Logs  message with paramNames and values  of parameters  at <code>DEBUG</code> level.
     * </p>
     *
     * @param message    the message
     * @param paramNames the names of parameters to log (not Null).
     * @param params     the values of parameters to log (not Null).
     */
    public static void logMessage(String message, String[] paramNames, Object[] params) {
        if (log.isDebugEnabled()) {
            log.debug(message + toString(paramNames, params));
        }
    }

    /**
     * <p>
     * Logs  message at <code>DEBUG</code> level.
     * </p>
     *
     * @param message the message
     */
    public static void logMessage(String message) {
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    /**
     * <p>
     * Logs  message at <code>WARN</code> level.
     * </p>
     *
     * @param message the message
     */
    public static void logWarn(String message) {
        log.warn(message);
    }


    /**
     * <p>
     * Logs for entrance into public methods at <code>DEBUG</code> level.
     * </p>
     *
     * @param signature  the signature.
     * @param paramNames the names of parameters to log (not Null).
     * @param params     the values of parameters to log (not Null).
     */
    public static void logEntrance(String signature, String[] paramNames, Object[] params) {
        if (log.isDebugEnabled()) {
            String msg = String.format(MESSAGE_ENTRANCE, signature) + toString(paramNames, params);
            log.debug(msg);
        }
    }

    /**
     * <p>
     * Logs for exit from public methods at <code>DEBUG</code> level.
     * </p>
     *
     * @param signature the signature of the method to be logged.
     * @param value     the return value to log.
     */
    public static void logExit(String signature, Object value) {
        if (log.isDebugEnabled()) {
            StringBuilder msg = new StringBuilder(String.format(MESSAGE_EXIT, signature));
            if (value != null) {
                msg.append(" Output parameter : ").append(value);
            }
            log.debug(msg.toString());
        }
    }

    /**
     * <p>
     * Logs the given exception and message at <code>ERROR</code> level.
     * </p>
     *
     * @param <T>       the exception type.
     * @param signature the signature of the method to log.
     * @param e         the exception to log.
     */
    public static <T extends Throwable> void logException(String signature, T e) {
        StringBuilder sw = new StringBuilder();
        sw.append(String.format(MESSAGE_ERROR, signature))
                .append(e.getClass().getName())
                .append(": ")
                .append(e.getMessage());
        Throwable cause = e.getCause();
        final String lineSeparator = System.getProperty("line.separator");
        while (null != cause) {
            sw.append(lineSeparator)
                    .append("        Caused By: ")
                    .append(cause.getClass().getName())
                    .append(": ")
                    .append(cause.getMessage());
            cause = cause.getCause();
        }

        if (e instanceof AssignmentException) {
            log.error(sw.toString());
        } else {
            log.error(sw.toString(), e);
        }

    }

    /**
     * <p>
     * Converts the parameters to string.
     * </p>
     *
     * @param paramNames the names of parameters.
     * @param params     the values of parameters.
     * @return the string
     */
    private static String toString(String[] paramNames, Object[] params) {
        StringBuilder sb = new StringBuilder(" Input parameters: {");
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(paramNames[i]).append(":").append(toString(params[i]));
            }
        }
        sb.append("}.");
        return sb.toString();
    }

    /**
     * <p>
     * Converts the object to string.
     * </p>
     *
     * @param obj the object
     * @return the string representation of the object.
     */
    public static String toString(Object obj) {
        String result;
        try {
            if (obj instanceof HttpServletRequest) {
                result = "Spring provided servlet request";
            } else if (obj instanceof HttpServletResponse) {
                result = "Spring provided servlet response";
            } else if (obj instanceof ModelAndView) {
                result = "Spring provided model and view object";
            } else {
                result = MAPPER.writeValueAsString(obj);
            }

        } catch (JsonProcessingException e) {
            result = "The object can not be serialized by Jackson JSON mapper, error: " + e.getMessage();
        }
        return result;
    }

    public static boolean createDirectoryIfNotExist(File dir) {
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                return false;
            }
        }
        return true;
    }


    public static String addRandomStringInFileName(String filename) {

        StringBuilder newFileName = new StringBuilder();
        filename = filename.replaceAll("\\s+", "-");
        if (filename.contains(".")) {
            newFileName.append(filename.substring(0, filename.lastIndexOf('.')));
            newFileName.append('-');
            newFileName.append(UUID.randomUUID().toString().split("-")[0]);
            newFileName.append(filename.substring(filename.lastIndexOf('.')));
        } else {
            newFileName.append(filename);
            newFileName.append('-');
            newFileName.append(UUID.randomUUID().toString().split("-")[0]);
        }

        return newFileName.toString();


    }

    public static int calculateAge(LocalDate dateOfBirth) {
        if ((dateOfBirth != null)) {
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

    public static String convertLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return localDateTime.format(formatter);
        }

        return "";
    }

    public static String convertActiveDate(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            return localDateTime.format(formatter);
        }

        return "";
    }


    public static LocalDateTime convertToLocalDateTime(String strdate) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(strdate, formatter);
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a");
        if (localDateTime == null) {
            return "";
        } else {
            return localDateTime.format(formatter);
        }
    }

}
