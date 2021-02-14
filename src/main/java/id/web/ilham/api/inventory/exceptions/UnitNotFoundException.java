package id.web.ilham.api.inventory.exceptions;

import org.springframework.http.HttpStatus;

public class UnitNotFoundException extends ApplicationException {

    public UnitNotFoundException(){
        super(HttpStatus.NOT_FOUND, "error." + HttpStatus.NOT_FOUND.value() + ".unit");
    }
}
