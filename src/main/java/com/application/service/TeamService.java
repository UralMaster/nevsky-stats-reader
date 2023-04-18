package com.application.service;

import com.application.model.Principal;
import com.application.model.Team;
import com.application.repository.TeamRepository;
import com.application.security.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    private final SecurityService securityService;

    public TeamService(TeamRepository teamRepository,
                       SecurityService securityService)
    {
        this.teamRepository = teamRepository;
        this.securityService = securityService;
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

    public void deleteTeam(Team team) {
        //TODO: implement only logical delete, not physical
        teamRepository.delete(team);
    }

    public void saveTeam(Team team) {
        if (team == null) {
            //TODO: configure logger!
            System.err.println("Team is null. Are you sure you have connected your form to the application?");
            return;
        }
        if (team.getCreator() == null) {
            Principal creator = (Principal) securityService.getAuthenticatedUser();
            team.setCreator(creator);
            team.setCreated(LocalDateTime.now());
        } else {
            Principal editor = (Principal) securityService.getAuthenticatedUser();
            team.setEditor(editor);
            team.setEdited(LocalDateTime.now());
        }

        teamRepository.save(team);
    }

}
