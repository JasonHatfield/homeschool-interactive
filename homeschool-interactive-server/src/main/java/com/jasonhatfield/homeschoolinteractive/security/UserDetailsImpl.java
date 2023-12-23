package com.jasonhatfield.homeschoolinteractive.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

/**
 * Represents an implementation of the UserDetails interface.
 * This class provides the necessary information for user authentication and authorization.
 */
public record UserDetailsImpl(String username, String password,
                              Collection<? extends GrantedAuthority> authorities,
                              boolean accountNonExpired,
                              boolean accountNonLocked,
                              boolean credentialsNonExpired,
                              boolean enabled,
                              Long userId) implements UserDetails {

    /**
     * Retrieves the authorities granted to the user.
     *
     * @return the authorities granted to the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Retrieves the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the username used to authenticate the user.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Checks whether the user's account has expired.
     *
     * @return true if the user's account is not expired, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * Checks whether the user's account is locked.
     *
     * @return true if the user's account is not locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * Checks whether the user's credentials (password) have expired.
     *
     * @return true if the user's credentials are not expired, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * Checks whether the user is enabled or disabled.
     *
     * @return true if the user is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Retrieves the user's ID.
     *
     * @return the user's ID
     */
    public Long getUserId() {
        return userId;
    }
}
