package id.web.ilham.api.inventory.exceptions;

import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends ApplicationException {

    public ItemNotFoundException(){
        super(HttpStatus.NOT_FOUND, "error." + HttpStatus.NOT_FOUND.value() + ".item");
    }
}
