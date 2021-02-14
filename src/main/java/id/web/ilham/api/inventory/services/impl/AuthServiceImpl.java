package id.web.ilham.api.inventory.services.impl;

import id.web.ilham.api.inventory.entities.Role;
import id.web.ilham.api.inventory.entities.User;
import id.web.ilham.api.inventory.entities.UserRoles;
import id.web.ilham.api.inventory.exceptions.EmailException;
import id.web.ilham.api.inventory.exceptions.RoleException;
import id.web.ilham.api.inventory.exceptions.UsernameException;
import id.web.ilham.api.inventory.models.auth.SignupRequest;
import id.web.ilham.api.inventory.repositories.RoleRepository;
import id.web.ilham.api.inventory.repositories.UserRepository;
import id.web.ilham.api.inventory.repositories.UserRolesRepository;
import id.web.ilham.api.inventory.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRolesRepository UserRolesRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    @Override
    public boolean register(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UsernameException();
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailException();
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(RoleException::new);

        userRepository.save(user);
        UserRoles userRoles = new UserRoles(user.getId(), role.getId());
        UserRolesRepository.save(userRoles);
        return false;
    }
}
