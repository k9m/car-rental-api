package org.k9m.rental.config.exception;

import org.k9m.rental.api.model.ErrorObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler(value={ ApplicationException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
       final ApplicationException applicationException = (ApplicationException)ex;

        return new ResponseEntity<>(applicationException.toError(), new HttpHeaders(), applicationException.getStatusCode());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(
                new ErrorObject()
                        .statusCode(status.value())
                        .message(ex.getMessage()),
                headers,
                status);
    }
}