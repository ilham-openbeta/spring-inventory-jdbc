package id.web.ilham.api.inventory.exceptions;

import org.springframework.http.HttpStatus;

public class RoleException extends ApplicationException {

    public RoleException(){
        super(HttpStatus.NOT_FOUND, "error.role");
    }
}
