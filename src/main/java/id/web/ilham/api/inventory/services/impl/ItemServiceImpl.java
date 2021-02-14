package id.web.ilham.api.inventory.services.impl;

import id.web.ilham.api.inventory.entities.Item;
import id.web.ilham.api.inventory.entities.ItemWithUnit;
import id.web.ilham.api.inventory.repositories.ItemRepository;
import id.web.ilham.api.inventory.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends CommonServiceImpl<Item, Integer> implements ItemService {

    @Autowired
    public ItemServiceImpl(ItemRepository repository) {
        super(repository);
    }

    @Override
    public Boolean existsById(Integer id) {
        return ((ItemRepository) repository).existsById(id);
    }

    @Override
    public ItemWithUnit findByIdWithUnit(Integer id) {
        return ((ItemRepository) repository).findByIdWithUnit(id);
    }
}
