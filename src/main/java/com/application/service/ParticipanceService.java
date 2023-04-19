package com.application.service;

import com.application.model.*;
import com.application.repository.ParticipanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipanceService {

    private final ParticipanceRepository participanceRepository;

    public ParticipanceService(ParticipanceRepository participanceRepository) {
        this.participanceRepository = participanceRepository;
    }

    public List<Participance> findParticipanceByGame(Game game) {
        return participanceRepository.findByGameId(game.getId());
    }

}
