package id.web.ilham.api.inventory.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameException extends ApplicationException {

    public UsernameException(){
        super(HttpStatus.BAD_REQUEST, "error.username");
    }
}
