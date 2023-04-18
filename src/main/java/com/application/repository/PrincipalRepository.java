package com.application.repository;

import com.application.model.Principal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * {@link Principal} related implementation of Spring {@link JpaRepository}.
 *
 * @author Ilya Ryabukhin
 * @since 23.03.2023
 */
public interface PrincipalRepository extends JpaRepository<Principal, UUID>  {

    /**
     * Finds {@link Principal} by provided username
     * @param username of principal
     * @return {@link Principal} instance in case of success, null - otherwise
     */
    Principal findByUsername(String username);

}
