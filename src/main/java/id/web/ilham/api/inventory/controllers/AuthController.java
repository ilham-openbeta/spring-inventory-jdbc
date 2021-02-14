package id.web.ilham.api.inventory.controllers;

import id.web.ilham.api.inventory.configs.jwt.JwtUtils;
import id.web.ilham.api.inventory.models.ResponseMessage;
import id.web.ilham.api.inventory.models.UserDetailsImpl;
import id.web.ilham.api.inventory.models.auth.JwtResponse;
import id.web.ilham.api.inventory.models.auth.LoginRequest;
import id.web.ilham.api.inventory.models.auth.SignupRequest;
import id.web.ilham.api.inventory.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    JwtUtils jwtUtils;

    @Operation(summary = "Request token", description = "Request token with username and password", tags = {"authentication"})
    @PostMapping(value = "/signin", produces = "application/json")
    public ResponseMessage<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse data = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

        return ResponseMessage.success(data);
    }

    @Operation(
            summary = "Create a new user account",
            description = "Create a new user account to give data editing access from API.",
            tags = {"authentication"})
    @PostMapping(value = "/signup", produces = "application/json")
    public ResponseMessage<String> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authService.register(signUpRequest);
        return ResponseMessage.success("User registered successfully!");
    }
}