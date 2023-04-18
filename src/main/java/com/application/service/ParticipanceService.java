package com.application.service;

import com.application.model.*;
import com.application.repository.ParticipanceRepository;
import com.application.security.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipanceService {

    private final ParticipanceRepository participanceRepository;

    private final SecurityService securityService;

    private final PlayerService playerService;

    public ParticipanceService(ParticipanceRepository participanceRepository,
                               SecurityService securityService,
                               PlayerService playerService)
    {
        this.participanceRepository = participanceRepository;
        this.securityService = securityService;
        this.playerService = playerService;
    }

    public List<Participance> findParticipanceByGame(Game game) {
        return participanceRepository.findByGameId(game.getId());
    }

    public void deleteParticipance(Participance participance) {
        Optional<Player> playerOptional = playerService.findPlayerById(participance.getPlayer().getId());
        if (playerOptional.isEmpty()) {
            //TODO: configure logger!
            System.err.println("Player for this participance not exists. Please check consistence of your database");
            return;
        }
        Player player = playerOptional.get();

        String teamName = participance.getGame().getNevskyTeam().getName();

        if (participance.getGame().getHistoricalStatus() == Game.HistoricalStatus.HISTORICAL) {
            //If game is historic

            if ("Невский 26".equals(teamName)) {
                deleteHistoricN26Participance(participance, player);
            }
            if ("Невский 54".equals(teamName)) {
                deleteHistoricN54Participance(participance, player);
            }

        } else {
            //If game is actual

            if ("Невский 26".equals(teamName)) {
                deleteActualN26Participance(participance, player);
            }
            if ("Невский 54".equals(teamName)) {
                deleteActualN54Participance(participance, player);
            }
        }

        playerService.savePlayer(player);
        participanceRepository.delete(participance);
    }

    public void saveParticipance(Participance participance) {
        if (participance == null) {
            //TODO: configure logger!
            System.err.println("Participance is null. Are you sure you have connected your form to the application?");
            return;
        }

        Optional<Player> playerOptional = playerService.findPlayerById(participance.getPlayer().getId());
        if (playerOptional.isEmpty()) {
            //TODO: configure logger!
            System.err.println("Player for this participance not exists. Please check consistence of your database");
            return;
        }
        Player player = playerOptional.get();

        String teamName = participance.getGame().getNevskyTeam().getName();

        Optional<Participance> oldParticipanceOptional = participanceRepository.findById(participance.getId());

        if (oldParticipanceOptional.isPresent()) {
            //If such participance was created earlier

            Participance oldParticipance = oldParticipanceOptional.get();

            if (participance.getGame().getHistoricalStatus() == Game.HistoricalStatus.HISTORICAL) {
                //If game is historic

                if ("Невский 26".equals(teamName)) {
                    updateHistoricN26Participance(participance, oldParticipance, player);
                }
                if ("Невский 54".equals(teamName)) {
                    updateHistoricN54Participance(participance, oldParticipance, player);
                }

            } else {
                //If game is actual

                if ("Невский 26".equals(teamName)) {
                    updateActualN26Participance(participance, oldParticipance, player);
                }
                if ("Невский 54".equals(teamName)) {
                    updateActualN54Participance(participance, oldParticipance, player);
                }
            }

        } else {
            //If this participance is absolutely new

            if (participance.getGame().getHistoricalStatus() == Game.HistoricalStatus.HISTORICAL) {
                //If game is historic

                if ("Невский 26".equals(teamName)) {
                    saveNewHistoricN26Participance(participance, player);
                }
                if ("Невский 54".equals(teamName)) {
                    saveNewHistoricN54Participance(participance, player);
                }

            } else {
                //If game is actual

                if ("Невский 26".equals(teamName)) {
                    saveNewActualN26Participance(participance, player);
                }
                if ("Невский 54".equals(teamName)) {
                    saveNewActualN54Participance(participance, player);
                }

            }

        }

        Principal editor = (Principal) securityService.getAuthenticatedUser();

        player.setEditor(editor);
        player.setEdited(LocalDateTime.now());

        if (participance.getCreator() == null) {
            participance.setCreator(editor);
            participance.setCreated(LocalDateTime.now());
        } else {
            participance.setEditor(editor);
            participance.setEdited(LocalDateTime.now());
        }

        playerService.savePlayer(player);
        participanceRepository.save(participance);

    }

    private void saveNewHistoricN26Participance(Participance newParticipance,
                                                Player player)
    {
        int goalsDelta = newParticipance.getGoals();
        int assistsDelta = newParticipance.getAssists();
        int yellowCardsDelta = newParticipance.getYellowCards();
        int redCardsDelta = newParticipance.getRedCards();

        player.setGames26Old(player.getGames26Old() - 1);
        player.setGames26New(player.getGames26New() + 1);

        player.setGoals26Old(player.getGoals26Old() - goalsDelta);
        player.setGoals26New(player.getGoals26New() + goalsDelta);

        player.setAssists26Old(player.getAssists26Old() - assistsDelta);
        player.setAssists26New(player.getAssists26New() + assistsDelta);

        player.setYellowCardsOld(player.getYellowCardsOld() - yellowCardsDelta);
        player.setYellowCardsNew(player.getYellowCardsNew() + yellowCardsDelta);

        player.setRedCardsOld(player.getRedCardsOld() - redCardsDelta);
        player.setRedCardsNew(player.getRedCardsNew() + redCardsDelta);

    }

    private void saveNewHistoricN54Participance(Participance newParticipance,
                                                Player player)
    {
        int goalsDelta = newParticipance.getGoals();
        int assistsDelta = newParticipance.getAssists();
        int yellowCardsDelta = newParticipance.getYellowCards();
        int redCardsDelta = newParticipance.getRedCards();

        player.setGames54Old(player.getGames54Old() - 1);
        player.setGames54New(player.getGames54New() + 1);

        player.setGoals54Old(player.getGoals54Old() - goalsDelta);
        player.setGoals54New(player.getGoals54New() + goalsDelta);

        player.setAssists54Old(player.getAssists54Old() - assistsDelta);
        player.setAssists54New(player.getAssists54New() + assistsDelta);

        player.setYellowCardsOld(player.getYellowCardsOld() - yellowCardsDelta);
        player.setYellowCardsNew(player.getYellowCardsNew() + yellowCardsDelta);

        player.setRedCardsOld(player.getRedCardsOld() - redCardsDelta);
        player.setRedCardsNew(player.getRedCardsNew() + redCardsDelta);

    }

    private void updateHistoricN26Participance(Participance newParticipance,
                                               Participance oldParticipance,
                                               Player player)
    {
        int goalsDelta = newParticipance.getGoals() - oldParticipance.getGoals();
        int assistsDelta = newParticipance.getAssists() - oldParticipance.getAssists();
        int yellowCardsDelta = newParticipance.getYellowCards() - oldParticipance.getYellowCards();
        int redCardsDelta = newParticipance.getRedCards() - oldParticipance.getRedCards();

        player.setGoals26Old(player.getGoals26Old() - goalsDelta);
        player.setGoals26New(player.getGoals26New() + goalsDelta);

        player.setAssists26Old(player.getAssists26Old() - assistsDelta);
        player.setAssists26New(player.getAssists26New() + assistsDelta);

        player.setYellowCardsOld(player.getYellowCardsOld() - yellowCardsDelta);
        player.setYellowCardsNew(player.getYellowCardsNew() + yellowCardsDelta);

        player.setRedCardsOld(player.getRedCardsOld() - redCardsDelta);
        player.setRedCardsNew(player.getRedCardsNew() + redCardsDelta);

    }

    private void updateHistoricN54Participance(Participance newParticipance,
                                               Participance oldParticipance,
                                               Player player)
    {
        int goalsDelta = newParticipance.getGoals() - oldParticipance.getGoals();
        int assistsDelta = newParticipance.getAssists() - oldParticipance.getAssists();
        int yellowCardsDelta = newParticipance.getYellowCards() - oldParticipance.getYellowCards();
        int redCardsDelta = newParticipance.getRedCards() - oldParticipance.getRedCards();

        player.setGoals54Old(player.getGoals54Old() - goalsDelta);
        player.setGoals54New(player.getGoals54New() + goalsDelta);

        player.setAssists54Old(player.getAssists54Old() - assistsDelta);
        player.setAssists54New(player.getAssists54New() + assistsDelta);

        player.setYellowCardsOld(player.getYellowCardsOld() - yellowCardsDelta);
        player.setYellowCardsNew(player.getYellowCardsNew() + yellowCardsDelta);

        player.setRedCardsOld(player.getRedCardsOld() - redCardsDelta);
        player.setRedCardsNew(player.getRedCardsNew() + redCardsDelta);

    }

    private void saveNewActualN26Participance(Participance newParticipance,
                                              Player player)
    {
        int goalsDelta = newParticipance.getGoals();
        int assistsDelta = newParticipance.getAssists();
        int yellowCardsDelta = newParticipance.getYellowCards();
        int redCardsDelta = newParticipance.getRedCards();

        player.setGames26New(player.getGames26New() + 1);

        player.setGoals26New(player.getGoals26New() + goalsDelta);

        player.setAssists26New(player.getAssists26New() + assistsDelta);

        player.setYellowCardsNew(player.getYellowCardsNew() + yellowCardsDelta);

        player.setRedCardsNew(player.getRedCardsNew() + redCardsDelta);
    }

    private void saveNewActualN54Participance(Participance newParticipance,
                                              Player player)
    {
        int goalsDelta = newParticipance.getGoals();
        int assistsDelta = newParticipance.getAssists();
        int yellowCardsDelta = newParticipance.getYellowCards();
        int redCardsDelta = newParticipance.getRedCards();

        player.setGames54New(player.getGames54New() + 1);

        player.setGoals54New(player.getGoals54New() + goalsDelta);

        player.setAssists54New(player.getAssists54New() + assistsDelta);

        player.setYellowCardsNew(player.getYellowCardsNew() + yellowCardsDelta);

        player.setRedCardsNew(player.getRedCardsNew() + redCardsDelta);
    }

    private void updateActualN26Participance(Participance newParticipance,
                                             Participance oldParticipance,
                                             Player player)
    {
        int goalsDelta = newParticipance.getGoals() - oldParticipance.getGoals();
        int assistsDelta = newParticipance.getAssists() - oldParticipance.getAssists();
        int yellowCardsDelta = newParticipance.getYellowCards() - oldParticipance.getYellowCards();
        int redCardsDelta = newParticipance.getRedCards() - oldParticipance.getRedCards();

        player.setGoals26New(player.getGoals26New() + goalsDelta);

        player.setAssists26New(player.getAssists26New() + assistsDelta);

        player.setYellowCardsNew(player.getYellowCardsNew() + yellowCardsDelta);

        player.setRedCardsNew(player.getRedCardsNew() + redCardsDelta);
    }

    private void updateActualN54Participance(Participance newParticipance,
                                             Participance oldParticipance,
                                             Player player)
    {
        int goalsDelta = newParticipance.getGoals() - oldParticipance.getGoals();
        int assistsDelta = newParticipance.getAssists() - oldParticipance.getAssists();
        int yellowCardsDelta = newParticipance.getYellowCards() - oldParticipance.getYellowCards();
        int redCardsDelta = newParticipance.getRedCards() - oldParticipance.getRedCards();

        player.setGoals54New(player.getGoals54New() + goalsDelta);

        player.setAssists54New(player.getAssists54New() + assistsDelta);

        player.setYellowCardsNew(player.getYellowCardsNew() + yellowCardsDelta);

        player.setRedCardsNew(player.getRedCardsNew() + redCardsDelta);

    }

    private void deleteHistoricN26Participance(Participance participance, Player player) {
        int goalsDelta = participance.getGoals();
        int assistsDelta = participance.getAssists();
        int yellowCardsDelta = participance.getYellowCards();
        int redCardsDelta = participance.getRedCards();

        player.setGames26Old(player.getGames26Old() + 1);
        player.setGames26New(player.getGames26New() - 1);

        player.setGoals26Old(player.getGoals26Old() + goalsDelta);
        player.setGoals26New(player.getGoals26New() - goalsDelta);

        player.setAssists26Old(player.getAssists26Old() + assistsDelta);
        player.setAssists26New(player.getAssists26New() - assistsDelta);

        player.setYellowCardsOld(player.getYellowCardsOld() + yellowCardsDelta);
        player.setYellowCardsNew(player.getYellowCardsNew() - yellowCardsDelta);

        player.setRedCardsOld(player.getRedCardsOld() + redCardsDelta);
        player.setRedCardsNew(player.getRedCardsNew() - redCardsDelta);

    }

    private void deleteHistoricN54Participance(Participance participance, Player player) {
        int goalsDelta = participance.getGoals();
        int assistsDelta = participance.getAssists();
        int yellowCardsDelta = participance.getYellowCards();
        int redCardsDelta = participance.getRedCards();

        player.setGames54Old(player.getGames54Old() + 1);
        player.setGames54New(player.getGames54New() - 1);

        player.setGoals54Old(player.getGoals54Old() + goalsDelta);
        player.setGoals54New(player.getGoals54New() - goalsDelta);

        player.setAssists54Old(player.getAssists54Old() + assistsDelta);
        player.setAssists54New(player.getAssists54New() - assistsDelta);

        player.setYellowCardsOld(player.getYellowCardsOld() + yellowCardsDelta);
        player.setYellowCardsNew(player.getYellowCardsNew() - yellowCardsDelta);

        player.setRedCardsOld(player.getRedCardsOld() + redCardsDelta);
        player.setRedCardsNew(player.getRedCardsNew() - redCardsDelta);

    }

    private void deleteActualN26Participance(Participance participance, Player player) {
        int goalsDelta = participance.getGoals();
        int assistsDelta = participance.getAssists();
        int yellowCardsDelta = participance.getYellowCards();
        int redCardsDelta = participance.getRedCards();

        player.setGames26New(player.getGames26New() - 1);

        player.setGoals26New(player.getGoals26New() - goalsDelta);

        player.setAssists26New(player.getAssists26New() - assistsDelta);

        player.setYellowCardsNew(player.getYellowCardsNew() - yellowCardsDelta);

        player.setRedCardsNew(player.getRedCardsNew() - redCardsDelta);
    }

    private void deleteActualN54Participance(Participance participance, Player player) {
        int goalsDelta = participance.getGoals();
        int assistsDelta = participance.getAssists();
        int yellowCardsDelta = participance.getYellowCards();
        int redCardsDelta = participance.getRedCards();

        player.setGames54New(player.getGames54New() - 1);

        player.setGoals54New(player.getGoals54New() - goalsDelta);

        player.setAssists54New(player.getAssists54New() - assistsDelta);

        player.setYellowCardsNew(player.getYellowCardsNew() - yellowCardsDelta);

        player.setRedCardsNew(player.getRedCardsNew() - redCardsDelta);
    }

}
