package com.application.service;

import com.application.model.Player;
import com.application.repository.PlayerRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service which contains {@link Player}s related logic - search, count, etc.
 *
 * @author Ilya Ryabukhin
 * @since 18.04.2023
 */
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Finds all players with optional applying of name related filter
     *
     * @param stringFilter filter for searching by name
     * @return list of {@link Player}s
     */
    @NonNull
    public List<Player> findAllPlayersByName(@Nullable String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return playerRepository.findAll();
        } else {
            return playerRepository.searchByName(stringFilter);
        }
    }

    /**
     * Finds all active players
     *
     * @return list of all active {@link Player}s
     */
    @NonNull
    public List<Player> findAllActivePlayers() {
        return playerRepository.searchOnlyActive();
    }

    public long countPlayers() {
        return playerRepository.count();
    }

}
