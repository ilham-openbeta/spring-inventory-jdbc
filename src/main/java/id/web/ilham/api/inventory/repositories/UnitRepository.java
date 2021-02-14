package id.web.ilham.api.inventory.repositories;

import id.web.ilham.api.inventory.entities.Unit;

public interface UnitRepository extends CommonRepository<Unit, Integer> {
    Boolean existsById(Integer id);
}
