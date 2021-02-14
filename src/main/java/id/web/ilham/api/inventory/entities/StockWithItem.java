package id.web.ilham.api.inventory.entities;

public class StockWithItem {

    private Integer id;

    private Item item;

    private Integer quantity;

    public StockWithItem() {
    }

    public StockWithItem(Item item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public StockWithItem(Integer id, Item item, Integer quantity) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
