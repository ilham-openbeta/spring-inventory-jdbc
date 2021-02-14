package id.web.ilham.api.inventory.services;

import id.web.ilham.api.inventory.entities.Unit;

public interface UnitService extends CommonService<Unit, Integer> {
    Boolean existsById(Integer id);
}
