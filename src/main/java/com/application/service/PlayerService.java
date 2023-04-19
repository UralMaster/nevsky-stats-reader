package com.application.service;

import com.application.model.Player;
import com.application.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAllPlayers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return playerRepository.findAll();
        } else {
            return playerRepository.search(stringFilter);
        }
    }

    public Optional<Player> findPlayerById(UUID id) {
        return playerRepository.findById(id);
    }

    public long countPlayers() {
        return playerRepository.count();
    }

}
