package com.landry.transfertdedevise.security.controler;

import com.landry.transfertdedevise.compte.exception.errorexceptions.UserNotFoundException;
import com.landry.transfertdedevise.security.dto.AuthenticationRequest;
import com.landry.transfertdedevise.security.dto.AuthenticationResponse;
import com.landry.transfertdedevise.security.dto.ChangePasswordRequest;
import com.landry.transfertdedevise.security.dto.RegisterRequest;
import com.landry.transfertdedevise.security.dto.UserDto;
import com.landry.transfertdedevise.security.exception.PasswordException;
import com.landry.transfertdedevise.security.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity(userService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    @SecurityRequirement(name = "Bearer Authentication")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }

    @PatchMapping("/change-password")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) throws PasswordException, PasswordException {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/compte")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsersWithComptes());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUser(id));
    }


}
