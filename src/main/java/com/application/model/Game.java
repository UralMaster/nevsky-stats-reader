package com.application.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Contains main info about particular game.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
@Entity
public class Game extends AbstractEntity {

    /**
     * Enumeration with game statuses
     */
    public enum HistoricalStatus {
        NEW_AGE("Актуальная"),
        HISTORICAL("Историческая");

        private final String textualStatus;

        /**
         * Constructor
         *
         * @param textualStatus textual description/name of game status
         */
        HistoricalStatus(@NonNull String textualStatus) {
            this.textualStatus = textualStatus;
        }

        /**
         * Gets textual description/name of game status
         *
         * @return textual description/name of game status
         */
        @NonNull
        public String getTextualStatus() {
            return textualStatus;
        }
    }

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

    @Column(name = "historical_status")
    @Enumerated(EnumType.STRING)
    private HistoricalStatus historicalStatus;

    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator")
    private Principal creator;

    private LocalDateTime edited;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editor")
    private Principal editor;

    private LocalDateTime removed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "remover")
    private Principal remover;

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

    @Nullable
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(@NonNull LocalDateTime created) {
        this.created = created;
    }

    @Nullable
    public Principal getCreator() {
        return creator;
    }

    public void setCreator(@NonNull Principal creator) {
        this.creator = creator;
    }

    @Nullable
    public LocalDateTime getEdited() {
        return edited;
    }

    public void setEdited(@NonNull LocalDateTime edited) {
        this.edited = edited;
    }

    @Nullable
    public Principal getEditor() {
        return editor;
    }

    public void setEditor(@NonNull Principal editor) {
        this.editor = editor;
    }

    @Nullable
    public LocalDateTime getRemoved() {
        return removed;
    }

    public void setRemoved(@NonNull LocalDateTime removed) {
        this.removed = removed;
    }

    @Nullable
    public Principal getRemover() {
        return remover;
    }

    public void setRemover(@NonNull Principal remover) {
        this.remover = remover;
    }

    @NonNull
    public HistoricalStatus getHistoricalStatus() {
        return historicalStatus;
    }

    public void setHistoricalStatus(@Nullable HistoricalStatus historicalStatus) {
        this.historicalStatus = historicalStatus;
    }
}

