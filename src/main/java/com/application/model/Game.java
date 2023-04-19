package com.application.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Contains main info about particular game.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
@Entity
public class Game extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nevsky_team")
    private Team nevskyTeam;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "opposite_team")
    private Team oppositeTeam;

    @Column(name = "nevsky_goals")
    private int nevskyGoals;
    @Column(name = "opposite_goals")
    private int oppositeGoals;

    private LocalDate gamedate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tournament")
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "season")
    private Season season;

    /**
     * Constructor
     */
    public Game() {
    }

    /**
     * Gets team for Nevsky side
     *
     * @return team for Nevsky side
     */
    @NonNull
    public Team getNevskyTeam() {
        return nevskyTeam;
    }

    /**
     * Sets team for Nevsky side
     *
     * @param nevskyTeam team for Nevsky side
     */
    public void setNevskyTeam(@NonNull Team nevskyTeam) {
        this.nevskyTeam = nevskyTeam;
    }

    /**
     * Gets team for opposite side
     *
     * @return team for opposite side
     */
    @NonNull
    public Team getOppositeTeam() {
        return oppositeTeam;
    }

    /**
     * Sets team for opposite side
     *
     * @param oppositeTeam team for opposite side
     */
    public void setOppositeTeam(@NonNull Team oppositeTeam) {
        this.oppositeTeam = oppositeTeam;
    }

    /**
     * Gets goals amount for Nevsky side
     *
     * @return goals amount for Nevsky side
     */
    public int getNevskyGoals() {
        return nevskyGoals;
    }

    /**
     * Sets goals amount for Nevsky side
     *
     * @param nevskyGoals goals amount for Nevsky side
     */
    public void setNevskyGoals(int nevskyGoals) {
        this.nevskyGoals = nevskyGoals;
    }

    /**
     * Gets goals amount for opposite side
     *
     * @return goals amount for opposite side
     */
    public int getOppositeGoals() {
        return oppositeGoals;
    }

    /**
     * Sets goals amount for opposite side
     *
     * @param oppositeGoals goals amount for opposite side
     */
    public void setOppositeGoals(int oppositeGoals) {
        this.oppositeGoals = oppositeGoals;
    }

    @NonNull
    public LocalDate getGamedate() {
        return gamedate;
    }

    public void setGamedate(@NonNull LocalDate gamedate) {
        this.gamedate = gamedate;
    }

    @NonNull
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(@NonNull Tournament tournament) {
        this.tournament = tournament;
    }

    @NonNull
    public Season getSeason() {
        return season;
    }

    public void setSeason(@NonNull Season season) {
        this.season = season;
    }

}

