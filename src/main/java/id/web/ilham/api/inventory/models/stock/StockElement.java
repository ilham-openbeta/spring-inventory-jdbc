package id.web.ilham.api.inventory.models.stock;

import id.web.ilham.api.inventory.models.item.ItemElement;

public class StockElement {

    private Integer id;
    private ItemElement item;
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemElement getItem() {
        return item;
    }

    public void setItem(ItemElement item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
