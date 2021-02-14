package id.web.ilham.api.inventory.repositories;

import id.web.ilham.api.inventory.entities.User;

import java.util.Optional;

public interface UserRepository extends CommonRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
