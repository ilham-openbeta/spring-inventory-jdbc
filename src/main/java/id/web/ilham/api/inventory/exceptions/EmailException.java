package id.web.ilham.api.inventory.exceptions;

import org.springframework.http.HttpStatus;

public class EmailException extends ApplicationException {

    public EmailException(){
        super(HttpStatus.BAD_REQUEST, "error.email");
    }
}
