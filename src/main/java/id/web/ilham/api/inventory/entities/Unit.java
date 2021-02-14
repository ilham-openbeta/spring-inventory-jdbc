package id.web.ilham.api.inventory.entities;

public class Unit {

    private Integer id;
    private String code;
    private String description;

    public Unit() {

    }

    public Unit(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Unit(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
