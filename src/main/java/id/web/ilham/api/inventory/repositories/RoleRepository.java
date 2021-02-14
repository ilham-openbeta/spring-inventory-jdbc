package id.web.ilham.api.inventory.repositories;

import id.web.ilham.api.inventory.entities.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CommonRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
