package id.web.ilham.api.inventory.repositories;

import id.web.ilham.api.inventory.entities.Item;
import id.web.ilham.api.inventory.entities.ItemWithUnit;

public interface ItemRepository extends CommonRepository<Item, Integer> {
    Boolean existsById(Integer id);

    ItemWithUnit findByIdWithUnit(Integer id);
}
