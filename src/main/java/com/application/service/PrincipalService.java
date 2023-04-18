package com.application.service;

import com.application.model.Principal;
import com.application.repository.PrincipalRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * {@link Principal} related implementation of Spring {@link UserDetailsService}
 *
 * @author Ilya Ryabukhin
 * @since 23.03.2023
 */
@Service
public class PrincipalService implements UserDetailsService {

    private final PrincipalRepository principalRepository;

    /**
     * Constructor
     *
     * @param principalRepository {@link PrincipalRepository} instance
     */
    public PrincipalService(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }

    /**
     * Finds {@link Principal} by {@link UUID}
     *
     * @param uuid {@link UUID} of principal
     * @return {@link Principal} instance
     */
    public Principal findPrincipalById(UUID uuid) {
        return principalRepository.findById(uuid).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Principal principal = principalRepository.findByUsername(username);
        if (principal == null) {
            throw new UsernameNotFoundException(username);
        }
        return principal;
    }
}
