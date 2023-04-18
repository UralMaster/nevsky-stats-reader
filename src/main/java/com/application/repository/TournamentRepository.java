package com.application.repository;

import com.application.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * {@link Tournament} related implementation of Spring {@link JpaRepository}.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
public interface TournamentRepository extends JpaRepository<Tournament, UUID> {

    @Query("select c from Tournament c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%'))")
    List<Tournament> search(@Param("searchTerm") String searchTerm);
}
