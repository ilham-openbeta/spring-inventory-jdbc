package id.web.ilham.api.inventory.services;

import id.web.ilham.api.inventory.entities.Item;
import id.web.ilham.api.inventory.entities.ItemWithUnit;

public interface ItemService extends CommonService<Item, Integer> {
    Boolean existsById(Integer id);

    ItemWithUnit findByIdWithUnit(Integer id);
}
