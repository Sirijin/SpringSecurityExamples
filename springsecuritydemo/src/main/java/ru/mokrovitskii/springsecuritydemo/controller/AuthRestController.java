package ru.mokrovitskii.springsecuritydemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mokrovitskii.springsecuritydemo.dto.AuthenticationDto;
import ru.mokrovitskii.springsecuritydemo.model.User;
import ru.mokrovitskii.springsecuritydemo.repository.UserRepository;
import ru.mokrovitskii.springsecuritydemo.security.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationDto authenticationDto) {
        String email = authenticationDto.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, authenticationDto.getPassword()));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
        String token = jwtTokenProvider.createToken(email, user.getRole().name());

        Map<Object, Object> response = new HashMap<>();

        response.put("email", email);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();
        handler.logout(request, response, null);
        return ResponseEntity.ok().build();
    }
}
