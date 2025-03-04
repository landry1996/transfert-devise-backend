package com.landry.transfertdedevise.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landry.transfertdedevise.security.configs.JwtService;
import com.landry.transfertdedevise.security.dto.AuthenticationRequest;
import com.landry.transfertdedevise.security.dto.AuthenticationResponse;
import com.landry.transfertdedevise.security.dto.ChangePasswordRequest;
import com.landry.transfertdedevise.security.dto.RegisterRequest;
import com.landry.transfertdedevise.security.dto.UserDto;
import com.landry.transfertdedevise.security.entity.Token;
import com.landry.transfertdedevise.security.entity.User;
import com.landry.transfertdedevise.compte.exception.errorexceptions.UserNotFoundException;
import com.landry.transfertdedevise.security.enums.TokenType;
import com.landry.transfertdedevise.security.exception.PasswordException;
import com.landry.transfertdedevise.security.mapper.UserMapper;
import com.landry.transfertdedevise.security.repository.TokenRepository;
import com.landry.transfertdedevise.security.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static com.landry.transfertdedevise.security.utils.Utils.PASSWORD_ARE_NOT_THE_SAME;
import static com.landry.transfertdedevise.security.utils.Utils.WRONG_PASSWORD;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;


    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .createdAt(request.getCreatedAt())
                .role(request.getRole())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userMapper.toDtoWithUserId(user))
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * rafrechir le token de l'utilisateur present dans notre base de donnees
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findUserByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) throws PasswordException {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        /**
         *  check if the current password is correct
         */
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new PasswordException(WRONG_PASSWORD);
        }
        /**
         * check if the two new passwords are the same
         */
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new PasswordException(PASSWORD_ARE_NOT_THE_SAME);
        }

        /**
         * update the password
         */
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        /**
         * save the new password
         */
        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsersWithComptes() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDtoWithUserId)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDto getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toDto(user);
    }
}
