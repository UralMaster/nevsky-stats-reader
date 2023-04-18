package com.application.repository;

import com.application.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * {@link Player} related implementation of Spring {@link JpaRepository}.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
public interface PlayerRepository extends JpaRepository<Player, UUID> {

    @Query("select c from Player c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) ")
    List<Player> search(@Param("searchTerm") String searchTerm);

}
