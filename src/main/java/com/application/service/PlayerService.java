package com.application.service;

import com.application.model.Player;
import com.application.model.Principal;
import com.application.repository.PlayerRepository;
import com.application.security.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final SecurityService securityService;

    public PlayerService(PlayerRepository playerRepository,
                         SecurityService securityService)
    {
        this.playerRepository = playerRepository;
        this.securityService = securityService;
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

    public void deletePlayer(Player player) {
        //TODO: implement only logical delete, not physical
        playerRepository.delete(player);
    }

    public void savePlayer(Player player) {
        if (player == null) {
            //TODO: configure logger!
            System.err.println("Player is null. Are you sure you have connected your form to the application?");
            return;
        }

        player.setGames26(player.getGames26Old() + player.getGames26New());
        player.setGames54(player.getGames54Old() + player.getGames54New());
        player.setGamesFriendly(player.getGamesFriendlyOld() + player.getGamesFriendlyNew());
        player.setGames(player.getGames26() + player.getGames54() + player.getGamesFriendly());

        player.setGoals26(player.getGoals26Old() + player.getGoals26New());
        player.setGoals54(player.getGoals54Old() +  player.getGoals54New());
        player.setGoalsFriendly(player.getGoalsFriendlyOld() + player.getGoalsFriendlyNew());
        player.setGoals(player.getGoals26() + player.getGoals54() + player.getGoalsFriendly());

        player.setAssists26(player.getAssists26Old() + player.getAssists26New());
        player.setAssists54(player.getAssists54Old() + player.getAssists54New());
        player.setAssistsFriendly(player.getAssistsFriendlyOld() + player.getAssistsFriendlyNew());
        player.setAssists(player.getAssists26() + player.getAssists54() + player.getAssistsFriendly());

        player.setPoints26Old(player.getGoals26Old() + player.getAssists26Old());
        player.setPoints54Old(player.getGoals54Old() + player.getAssists54Old());
        player.setPointsFriendlyOld(player.getGoalsFriendlyOld() + player.getAssistsFriendlyOld());
        player.setPoints26New(player.getGoals26New() + player.getAssists26New());
        player.setPoints54New(player.getGoals54New() + player.getAssists54New());
        player.setPointsFriendlyNew(player.getGoalsFriendlyNew() + player.getAssistsFriendlyNew());
        player.setPoints26(player.getPoints26Old() + player.getPoints26New());
        player.setPoints54(player.getPoints54Old() + player.getPoints54New());
        player.setPointsFriendly(player.getPointsFriendlyOld() + player.getPointsFriendlyNew());
        player.setPoints(player.getPoints26() + player.getPoints54() + player.getPointsFriendly());

        player.setYellowCards(player.getYellowCardsOld() + player.getYellowCardsNew());

        player.setRedCards(player.getRedCardsOld() + player.getRedCardsNew());

        if (player.getCreator() == null) {
            Principal creator = (Principal) securityService.getAuthenticatedUser();
            player.setCreator(creator);
            player.setCreated(LocalDateTime.now());
        } else {
            Principal editor = (Principal) securityService.getAuthenticatedUser();
            player.setEditor(editor);
            player.setEdited(LocalDateTime.now());
        }

        playerRepository.save(player);
    }

}
