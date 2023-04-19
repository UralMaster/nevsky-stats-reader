package com.application.service;

import com.application.model.Season;
import com.application.repository.SeasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
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

}
