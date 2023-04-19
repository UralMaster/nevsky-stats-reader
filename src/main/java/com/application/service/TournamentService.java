package com.application.service;

import com.application.model.Tournament;
import com.application.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
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

}
