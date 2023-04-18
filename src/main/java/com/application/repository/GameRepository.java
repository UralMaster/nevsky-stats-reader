package com.application.repository;

import com.application.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * {@link Game} related implementation of Spring {@link JpaRepository}.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
public interface GameRepository  extends JpaRepository<Game, UUID> {

}
