package com.univ.gest.config;

import com.univ.gest.domain.entity.User;
import com.univ.gest.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User introuvable: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getPassword())
                .disabled(!u.isEnabled())
                .authorities(
                        u.getRoles().stream()
                                .map(r -> new SimpleGrantedAuthority(r.getName())) // ex: ROLE_ADMIN
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
