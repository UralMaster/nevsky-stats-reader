package com.application.repository;

import com.application.model.Participance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * {@link Participance} related implementation of Spring {@link JpaRepository}.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
public interface ParticipanceRepository extends JpaRepository<Participance, UUID> {

    @Query(value = "SELECT * FROM Participance WHERE game = :gameId", nativeQuery = true)
    List<Participance> findByGameId(UUID gameId);

}
