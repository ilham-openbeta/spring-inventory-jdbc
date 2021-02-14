package id.web.ilham.api.inventory.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException(){
        super(HttpStatus.UNAUTHORIZED, "error." + HttpStatus.UNAUTHORIZED.value());
    }
}
