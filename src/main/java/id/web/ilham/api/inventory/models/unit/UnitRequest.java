package id.web.ilham.api.inventory.models.unit;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UnitRequest {

    @NotBlank
    @Size(min = 1, max = 20)
    private String code;

    @NotBlank
    @Size(min = 1, max = 100)
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
