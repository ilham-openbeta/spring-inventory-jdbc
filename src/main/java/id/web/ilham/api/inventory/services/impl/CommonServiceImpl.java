package id.web.ilham.api.inventory.services.impl;

import id.web.ilham.api.inventory.repositories.CommonRepository;
import id.web.ilham.api.inventory.services.CommonService;

import java.util.List;

public abstract class CommonServiceImpl<T, ID> implements CommonService<T, ID> {

    protected final CommonRepository<T, ID> repository;

    protected CommonServiceImpl(CommonRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public void save(T entity) {
        repository.save(entity);
    }

    @Override
    public Boolean removeById(ID id) {
        return repository.removeById(id);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

}
