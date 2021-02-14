package id.web.ilham.api.inventory.entities;

public class Stock {

    private Integer id;

    private Integer itemId;

    private Integer quantity;

    public Stock(){}

    public Stock(Integer itemId, Integer quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Stock(Integer id, Integer itemId, Integer quantity) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
