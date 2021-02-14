package id.web.ilham.api.inventory.services;

import id.web.ilham.api.inventory.models.auth.SignupRequest;

public interface AuthService {
    boolean register(SignupRequest signUpRequest);
}
