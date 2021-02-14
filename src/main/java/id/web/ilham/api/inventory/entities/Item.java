package id.web.ilham.api.inventory.entities;

public class Item {
    private Integer id;

    private String name;

    private Integer price;

    private String imageUrl;

    private String originalFilename;

    private Integer unitId;

    public Item(){}

    public Item(String name, Integer price, String imageUrl, String originalFilename, Integer unitId) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.originalFilename = originalFilename;
        this.unitId = unitId;
    }

    public Item(Integer id, String name, Integer price, String imageUrl, String originalFilename, Integer unitId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.originalFilename = originalFilename;
        this.unitId = unitId;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
}
