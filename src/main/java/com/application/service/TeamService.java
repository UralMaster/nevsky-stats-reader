package com.application.service;

import com.application.model.Team;
import com.application.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAllTeams(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return teamRepository.findAll();
        } else {
            return teamRepository.search(stringFilter);
        }
    }

    public long countTeams() {
        return teamRepository.count();
    }

}
