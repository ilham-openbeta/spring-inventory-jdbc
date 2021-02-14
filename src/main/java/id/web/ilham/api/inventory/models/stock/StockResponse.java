package id.web.ilham.api.inventory.models.stock;

import id.web.ilham.api.inventory.models.item.ItemElement;

public class StockResponse {

    private Integer id;
    private ItemElement itemElement;
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemElement getItemElement() {
        return itemElement;
    }

    public void setItemElement(ItemElement itemElement) {
        this.itemElement = itemElement;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
