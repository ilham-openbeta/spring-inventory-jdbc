package id.web.ilham.api.inventory.repositories;

import java.util.List;

public interface CommonRepository<T, ID> {
    void save(T entity);

    Boolean removeById(ID id);

    T findById(ID id);

    List<T> findAll();
}
