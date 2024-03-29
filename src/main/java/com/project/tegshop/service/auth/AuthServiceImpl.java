package com.project.tegshop.service.auth;

import com.project.tegshop.dto.LoginDto;
import com.project.tegshop.dto.RefreshTokenDto;
import com.project.tegshop.dto.UserDto;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.exception.RegisterTokenException;
import com.project.tegshop.model.Cart;
import com.project.tegshop.model.RegisterToken;
import com.project.tegshop.model.Role;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.repository.RegisterTokenRepository;
import com.project.tegshop.repository.UserRepository;
import com.project.tegshop.security.JwtService;
import com.project.tegshop.shared.GenericMessage;
import com.project.tegshop.shared.response.TokenResponse;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService{
    @Value("${expiration.time.register.token}")
    private int expirationTime;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private RegisterTokenRepository registerTokenRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           RegisterTokenRepository registerTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.registerTokenRepository = registerTokenRepository;
    }

    @Override
    public String registerUser(UserDto userDto) throws UserException, RegisterTokenException {
        Optional<UserEntity> existsUser = userRepository.findByEmailId(userDto.getEmailId());

        if(existsUser.isPresent() ) {
            if(existsUser.get().getEnabled()) {
                throw new UserException(GenericMessage.EMAIL_ALREADY_IN_USE);
            }

            UserEntity currentUser = existsUser.get();
            currentUser.setFirstName(userDto.getFirstName());
            currentUser.setLastName(userDto.getLastName());
            currentUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(currentUser);

            RegisterToken existRegisterToken = registerTokenRepository.findByUserId(currentUser.getUserId())
                    .orElseThrow(() -> new RegisterTokenException(GenericMessage.REGISTRATION_TOKEN_NOT_FOUND));

            existRegisterToken.setToken(UUID.randomUUID().toString().substring(0, 6));
            existRegisterToken.setExpiredTime(LocalDateTime.now().plusMinutes(expirationTime));
            registerTokenRepository.save(existRegisterToken);

            return existRegisterToken.getToken();
        }

        UserEntity user = UserEntity.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .emailId(userDto.getEmailId())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.CUSTOMER)
                .cart(new Cart())
                .addresses(new ArrayList<>())
                .enabled(false)
                .build();
        userRepository.save(user);

        String registerToken = generateTokenRegister(user);

        return registerToken;
    }

    @Override
    public TokenResponse loginUser(LoginDto loginDto) throws UserException {
        UserEntity existsUser = userRepository.findByEmailId(loginDto.getEmailId())
                .orElseThrow(() -> new UserException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        if(!existsUser.getEnabled()) {
            throw new UserException(GenericMessage.USER_WAS_NOT_VERIFIED);
        }

        if(!passwordIsMatched(loginDto.getPassword(), existsUser.getPassword())) {
            throw new UserException(GenericMessage.PASSWORD_NOT_MATCH);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmailId(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtService.generateToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(authentication);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenDto refreshTokenDto)
            throws AuthenticationException, UserException {
        if(!jwtService.validateToken(refreshTokenDto.getRefreshToken())) {
            throw new AuthenticationException(GenericMessage.TOKEN_REFRESH_EXPIRED);
        }

        String userEmail = jwtService.getUsernameFromJWT(refreshTokenDto.getRefreshToken());

        UserEntity existsUser = userRepository.findByEmailId(userEmail)
                .orElseThrow(() -> new UserException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        String accessToken = jwtService.generateToken(existsUser.getEmailId());
        String refreshToken = jwtService.generateRefreshToken(existsUser.getEmailId());

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public String verifyRegistration(String token) throws RegisterTokenException {
        RegisterToken registerToken = registerTokenRepository.findByToken(token)
                .orElseThrow(() -> new RegisterTokenException(GenericMessage.REGISTRATION_TOKEN_NOT_FOUND));

        if(registerToken.getExpiredTime().isBefore(LocalDateTime.now())) {
            throw new RegisterTokenException(GenericMessage.REGISTRATION_TOKEN_IS_EXPIRED);
        }

        UserEntity user = registerToken.getUser();

        if(user.getEnabled())  {
            throw new RegisterTokenException(GenericMessage.USER_VERIFIED_REGISTRATION);
        }

        user.setEnabled(true);
        userRepository.save(user);

        return GenericMessage.REGISTER_SUCCESSFULLY;
    }

    private boolean passwordIsMatched(String password, String encodePassword) {
        return passwordEncoder.matches(password, encodePassword);
    }

    private String generateTokenRegister(UserEntity user) {
        RegisterToken registerToken = RegisterToken.builder()
                .expiredTime(LocalDateTime.now().plusMinutes(expirationTime))
                .token(UUID.randomUUID().toString().substring(0, 6))
                .user(user)
                .build();
        registerTokenRepository.save(registerToken);
        return registerToken.getToken();
    }
}
