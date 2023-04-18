package com.application.service;

import com.application.model.Game;
import com.application.model.Principal;
import com.application.repository.GameRepository;
import com.application.security.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    private final SecurityService securityService;

    public GameService(GameRepository gameRepository,
                       SecurityService securityService)
    {
        this.gameRepository = gameRepository;
        this.securityService = securityService;
    }

    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    public long countGames() {
        return gameRepository.count();
    }

    public void saveGame(Game game) {
        if (game == null) {
            //TODO: configure logger!
            System.err.println("Game is null. Are you sure you have connected your form to the application?");
            return;
        }
        if (game.getCreator() == null) {
            Principal creator = (Principal) securityService.getAuthenticatedUser();
            game.setCreator(creator);
            game.setCreated(LocalDateTime.now());
        } else {
            Principal editor = (Principal) securityService.getAuthenticatedUser();
            game.setEditor(editor);
            game.setEdited(LocalDateTime.now());
        }

        gameRepository.save(game);
    }

}
