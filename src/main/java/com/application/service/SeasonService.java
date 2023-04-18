package com.application.service;

import com.application.model.Principal;
import com.application.model.Season;
import com.application.repository.SeasonRepository;
import com.application.security.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;

    private final SecurityService securityService;

    public SeasonService(SeasonRepository seasonRepository,
                         SecurityService securityService)
    {
        this.seasonRepository = seasonRepository;
        this.securityService = securityService;
    }

    public List<Season> findAllSeasons(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return seasonRepository.findAll();
        } else {
            return seasonRepository.search(stringFilter);
        }
    }

    public long countSeasons() {
        return seasonRepository.count();
    }

    public void deleteSeason(Season season) {
        //TODO: implement only logical delete, not physical
        seasonRepository.delete(season);
    }

    public void saveSeason(Season season) {
        if (season == null) {
            //TODO: configure logger!
            System.err.println("Season is null. Are you sure you have connected your form to the application?");
            return;
        }

        if (season.getName() == null || season.getName().isEmpty()) {
            season.setName(season.getDivision().getDivisionName());
        }

        if (season.getCreator() == null) {
            Principal creator = (Principal) securityService.getAuthenticatedUser();
            season.setCreator(creator);
            season.setCreated(LocalDateTime.now());
        } else {
            Principal editor = (Principal) securityService.getAuthenticatedUser();
            season.setEditor(editor);
            season.setEdited(LocalDateTime.now());
        }

        seasonRepository.save(season);
    }

}
