package org.k9m.rental.config.exception;

import lombok.Getter;
import org.k9m.rental.api.model.ErrorObject;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    @Getter
    private HttpStatus statusCode;

    public ApplicationException(final HttpStatus statusCode, final String message){
        super(message);
        this.statusCode = statusCode;
    }

    public ErrorObject toError(){
        return new ErrorObject()
                .statusCode(statusCode.value())
                .message(getMessage());
    }

}
