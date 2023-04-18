package com.application.model;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

/**
 * Implementation of Spring {@link UserDetails}.
 * Contains main info about authenticated application user.
 *
 * @author Ilya Ryabukhin
 * @since 23.03.2023
 */
@Entity
public class Principal extends AbstractEntity implements UserDetails {

    @NotEmpty
    @Column(name = "name", nullable = false, unique = true)
    private String username;

    @NotEmpty
    private String password;

    /**
     * Constructor
     */
    public Principal() {
    }

    @Override
    @NonNull
    public String getUsername() {
        return username;
    }

    /**
     * Sets username
     *
     * @param username username for setting
     */
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @Override
    @NonNull
    public String getPassword() {
        return password;
    }

    /**
     * Sets password in encrypted form
     *
     * @param password in encrypted form
     */
    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
