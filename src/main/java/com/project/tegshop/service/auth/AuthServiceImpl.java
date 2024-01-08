package com.project.tegshop.service.auth;

import com.project.tegshop.dto.LoginDto;
import com.project.tegshop.dto.UserDto;
import com.project.tegshop.exception.AuthException;
import com.project.tegshop.model.Role;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.repository.UserRepository;
import com.project.tegshop.security.CustomUserDetailsService;
import com.project.tegshop.security.JwtService;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService{
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserEntity registerUser(UserDto userDto) throws AuthException {
        Optional<UserEntity> existsUser = userRepository.findByEmailId(userDto.getEmailId());
        if(existsUser.isPresent()) {
            throw new AuthException(GenericMessage.EMAIL_ALREADY_IN_USE);
        }

        UserEntity user = UserEntity.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .emailId(userDto.getEmailId())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.CUSTOMER)
                .addresses(new ArrayList<>())
                .enabled(false)
                .build();

        return userRepository.save(user);
    }

    @Override
    public String loginUser(LoginDto loginDto) throws AuthException {
        UserEntity existsUser = userRepository.findByEmailId(loginDto.getEmailId())
                .orElseThrow(() -> new AuthException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        if(!passwordIsMatched(loginDto.getPassword(), existsUser.getPassword())) {
            throw new AuthException(GenericMessage.PASSWORD_NOT_MATCH);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmailId(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtService.generateToken(authentication);

        return accessToken;
    }

    private boolean passwordIsMatched(String password, String encodePassword) {
        return passwordEncoder.matches(password, encodePassword);
    }
}
