package id.web.ilham.api.inventory.services.impl;

import id.web.ilham.api.inventory.entities.Unit;
import id.web.ilham.api.inventory.repositories.UnitRepository;
import id.web.ilham.api.inventory.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl extends CommonServiceImpl<Unit, Integer> implements UnitService {

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository) {
        super(unitRepository);
    }

    @Override
    public Boolean existsById(Integer id) {
        return ((UnitRepository) repository).existsById(id);
    }
}
