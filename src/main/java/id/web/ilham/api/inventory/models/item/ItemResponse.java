package id.web.ilham.api.inventory.models.item;

import id.web.ilham.api.inventory.models.unit.UnitResponse;

public class ItemResponse {

    private Integer id;
    private String name;
    private Integer price;
    private UnitResponse unitResponse;
    private String imageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public UnitResponse getUnitResponse() {
        return unitResponse;
    }

    public void setUnitResponse(UnitResponse unitResponse) {
        this.unitResponse = unitResponse;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
