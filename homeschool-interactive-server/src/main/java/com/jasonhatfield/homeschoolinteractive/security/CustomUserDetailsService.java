package com.jasonhatfield.homeschoolinteractive.security;

import com.jasonhatfield.homeschoolinteractive.model.User;
import com.jasonhatfield.homeschoolinteractive.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * This class implements the UserDetailsService interface and provides custom user details for authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a new CustomUserDetailsService with the specified UserRepository.
     *
     * @param userRepository the UserRepository used to retrieve user information
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user details by username.
     *
     * @param username the username of the user
     * @return the UserDetailsImpl object containing the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        String roleWithPrefix = "ROLE_" + user.getRole();

        return new UserDetailsImpl(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(roleWithPrefix)),
                true, // accountNonExpired
                true, // accountNonLocked
                true, // credentialsNonExpired
                true,  // enabled
                user.getUserId() // Set userId here
        );
    }
}
