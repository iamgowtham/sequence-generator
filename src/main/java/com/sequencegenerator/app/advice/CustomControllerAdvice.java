package com.sequencegenerator.app.advice;

import com.sequencegenerator.app.exception.BadRequestException;
import com.sequencegenerator.app.exception.ResourceNotFoundException;
import com.sequencegenerator.app.response.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice(annotations = RestController.class)
public class CustomControllerAdvice {
    private static final Logger logger = LogManager.getLogger (CustomControllerAdvice.class);
    private final MessageSource messageSource;

    @Autowired
    public CustomControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    /**
     * Utility Method to generate localized message for a list of field errors
     *
     * @param allErrors the field errors
     * @return the list
     */
    private List<String> processAllErrors(List<ObjectError> allErrors) {
        return allErrors.stream().map(this::resolveLocalizedErrorMessage).collect(Collectors.toList());
    }

    /**
     * Resolve localized error message. Utility method to generate a localized error
     * message
     *
     * @param objectError the field error
     * @return the string
     */
    private String resolveLocalizedErrorMessage(ObjectError objectError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(objectError, currentLocale);
        logger.info(localizedErrorMessage);
        return localizedErrorMessage;
    }

    private String resolvePathFromWebRequest(WebRequest request) {
        try {
            return ((ServletWebRequest) request).getRequest().getAttribute("javax.servlet.forward.request_uri").toString();
        } catch (Exception ex) {
            return null;
        }
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleBadRequestException(BadRequestException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

}
