package id.web.ilham.api.inventory.services;

import java.util.List;

public interface CommonService<T, ID> {
    void save(T entity);

    Boolean removeById(ID id);

    T findById(ID id);

    List<T> findAll();

}
