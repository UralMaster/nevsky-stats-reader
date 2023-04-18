package com.application.service;

import com.application.model.Principal;
import com.application.model.Tournament;
import com.application.repository.TournamentRepository;
import com.application.security.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    private final SecurityService securityService;

    public TournamentService(TournamentRepository tournamentRepository,
                             SecurityService securityService)
    {
        this.tournamentRepository = tournamentRepository;
        this.securityService = securityService;
    }

    public List<Tournament> findAllTournaments(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return tournamentRepository.findAll();
        } else {
            return tournamentRepository.search(stringFilter);
        }
    }

    public long countTournaments() {
        return tournamentRepository.count();
    }

    public void deleteTournament(Tournament tournament) {
        //TODO: implement only logical delete, not physical
        tournamentRepository.delete(tournament);
    }

    public void saveTournament(Tournament tournament) {
        if (tournament == null) {
            //TODO: configure logger!
            System.err.println("Tournament is null. Are you sure you have connected your form to the application?");
            return;
        }
        if (tournament.getCreator() == null) {
            Principal creator = (Principal) securityService.getAuthenticatedUser();
            tournament.setCreator(creator);
            tournament.setCreated(LocalDateTime.now());
        } else {
            Principal editor = (Principal) securityService.getAuthenticatedUser();
            tournament.setEditor(editor);
            tournament.setEdited(LocalDateTime.now());
        }

        tournamentRepository.save(tournament);
    }

}
