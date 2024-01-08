package com.project.tegshop.security;

import com.project.tegshop.model.Role;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.repository.UserRepository;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new UsernameNotFoundException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        return new User(user.getEmailId(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }
//Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
    private Collection<GrantedAuthority> mapRolesToAuthorities(Role role) {
        List<String> roles = new ArrayList<>();
        roles.add(role.name());

        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r))
                .collect(Collectors.toList());
    }
}
