package com.application.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Contains main info about player.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
@Entity
public class Player extends AbstractEntity {

    /**
     * Enumeration with player's activity status
     */
    public enum ActivityStatus {
        ACTIVE("Активен"),
        INACTIVE("Неактивен");

        private final String textualStatus;

        /**
         * Constructor
         *
         * @param textualStatus textual description/name of activity status
         */
        ActivityStatus(@NonNull String textualStatus) {
            this.textualStatus = textualStatus;
        }

        @NonNull
        public String getTextualStatus() {
            return textualStatus;
        }
    }

    @NotEmpty
    private String name = "";

    private int games;
    @Column(name = "games_26")
    private int games26;
    @Column(name = "games_54")
    private int games54;
    @Column(name = "games_friendly")
    private int gamesFriendly;
    @Column(name = "games_26_old")
    private int games26Old;
    @Column(name = "games_54_old")
    private int games54Old;
    @Column(name = "games_friendly_old")
    private int gamesFriendlyOld;
    @Column(name = "games_26_new")
    private int games26New;
    @Column(name = "games_54_new")
    private int games54New;
    @Column(name = "games_friendly_new")
    private int gamesFriendlyNew;

    private int goals;
    @Column(name = "goals_26")
    private int goals26;
    @Column(name = "goals_54")
    private int goals54;
    @Column(name = "goals_friendly")
    private int goalsFriendly;
    @Column(name = "goals_26_old")
    private int goals26Old;
    @Column(name = "goals_54_old")
    private int goals54Old;
    @Column(name = "goals_friendly_old")
    private int goalsFriendlyOld;
    @Column(name = "goals_26_new")
    private int goals26New;
    @Column(name = "goals_54_new")
    private int goals54New;
    @Column(name = "goals_friendly_new")
    private int goalsFriendlyNew;

    private int assists;
    @Column(name = "assists_26")
    private int assists26;
    @Column(name = "assists_54")
    private int assists54;
    @Column(name = "assists_friendly")
    private int assistsFriendly;
    @Column(name = "assists_26_old")
    private int assists26Old;
    @Column(name = "assists_54_old")
    private int assists54Old;
    @Column(name = "assists_friendly_old")
    private int assistsFriendlyOld;
    @Column(name = "assists_26_new")
    private int assists26New;
    @Column(name = "assists_54_new")
    private int assists54New;
    @Column(name = "assists_friendly_new")
    private int assistsFriendlyNew;

    private int points;
    @Column(name = "points_26")
    private int points26;
    @Column(name = "points_54")
    private int points54;
    @Column(name = "points_friendly")
    private int pointsFriendly;
    @Column(name = "points_26_old")
    private int points26Old;
    @Column(name = "points_54_old")
    private int points54Old;
    @Column(name = "points_friendly_old")
    private int pointsFriendlyOld;
    @Column(name = "points_26_new")
    private int points26New;
    @Column(name = "points_54_new")
    private int points54New;
    @Column(name = "points_friendly_new")
    private int pointsFriendlyNew;

    @Column(name = "yellow_cards")
    private int yellowCards;
    @Column(name = "yellow_cards_old")
    private int yellowCardsOld;
    @Column(name = "yellow_cards_new")
    private int yellowCardsNew;

    @Column(name = "red_cards")
    private int redCards;
    @Column(name = "red_cards_old")
    private int redCardsOld;
    @Column(name = "red_cards_new")
    private int redCardsNew;

    @Column(name = "activity_status")
    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    /**
     * Constructor
     */
    public Player() {
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getGames26Old() {
        return games26Old;
    }

    public void setGames26Old(int games26Old) {
        this.games26Old = games26Old;
    }

    public int getGames54Old() {
        return games54Old;
    }

    public void setGames54Old(int games54Old) {
        this.games54Old = games54Old;
    }

    public int getGamesFriendlyOld() {
        return gamesFriendlyOld;
    }

    public void setGamesFriendlyOld(int gamesFriendlyOld) {
        this.gamesFriendlyOld = gamesFriendlyOld;
    }

    public int getGames26New() {
        return games26New;
    }

    public void setGames26New(int games26New) {
        this.games26New = games26New;
    }

    public int getGames54New() {
        return games54New;
    }

    public void setGames54New(int games54New) {
        this.games54New = games54New;
    }

    public int getGamesFriendlyNew() {
        return gamesFriendlyNew;
    }

    public void setGamesFriendlyNew(int gamesFriendlyNew) {
        this.gamesFriendlyNew = gamesFriendlyNew;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getGoals26Old() {
        return goals26Old;
    }

    public void setGoals26Old(int goals26Old) {
        this.goals26Old = goals26Old;
    }

    public int getGoals54Old() {
        return goals54Old;
    }

    public void setGoals54Old(int goals54Old) {
        this.goals54Old = goals54Old;
    }

    public int getGoalsFriendlyOld() {
        return goalsFriendlyOld;
    }

    public void setGoalsFriendlyOld(int goalsFriendlyOld) {
        this.goalsFriendlyOld = goalsFriendlyOld;
    }

    public int getGoals26New() {
        return goals26New;
    }

    public void setGoals26New(int goals26New) {
        this.goals26New = goals26New;
    }

    public int getGoals54New() {
        return goals54New;
    }

    public void setGoals54New(int goals54New) {
        this.goals54New = goals54New;
    }

    public int getGoalsFriendlyNew() {
        return goalsFriendlyNew;
    }

    public void setGoalsFriendlyNew(int goalsFriendlyNew) {
        this.goalsFriendlyNew = goalsFriendlyNew;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getAssists26Old() {
        return assists26Old;
    }

    public void setAssists26Old(int assists26Old) {
        this.assists26Old = assists26Old;
    }

    public int getAssists54Old() {
        return assists54Old;
    }

    public void setAssists54Old(int assists54Old) {
        this.assists54Old = assists54Old;
    }

    public int getAssistsFriendlyOld() {
        return assistsFriendlyOld;
    }

    public void setAssistsFriendlyOld(int assistsFriendlyOld) {
        this.assistsFriendlyOld = assistsFriendlyOld;
    }

    public int getAssists26New() {
        return assists26New;
    }

    public void setAssists26New(int assists26New) {
        this.assists26New = assists26New;
    }

    public int getAssists54New() {
        return assists54New;
    }

    public void setAssists54New(int assists54New) {
        this.assists54New = assists54New;
    }

    public int getAssistsFriendlyNew() {
        return assistsFriendlyNew;
    }

    public void setAssistsFriendlyNew(int assistsFriendlyNew) {
        this.assistsFriendlyNew = assistsFriendlyNew;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints26Old() {
        return points26Old;
    }

    public void setPoints26Old(int points26Old) {
        this.points26Old = points26Old;
    }

    public int getPoints54Old() {
        return points54Old;
    }

    public void setPoints54Old(int points54Old) {
        this.points54Old = points54Old;
    }

    public int getPointsFriendlyOld() {
        return pointsFriendlyOld;
    }

    public void setPointsFriendlyOld(int pointsFriendlyOld) {
        this.pointsFriendlyOld = pointsFriendlyOld;
    }

    public int getPoints26New() {
        return points26New;
    }

    public void setPoints26New(int points26New) {
        this.points26New = points26New;
    }

    public int getPoints54New() {
        return points54New;
    }

    public void setPoints54New(int points54New) {
        this.points54New = points54New;
    }

    public int getPointsFriendlyNew() {
        return pointsFriendlyNew;
    }

    public void setPointsFriendlyNew(int pointsFriendlyNew) {
        this.pointsFriendlyNew = pointsFriendlyNew;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getYellowCardsOld() {
        return yellowCardsOld;
    }

    public void setYellowCardsOld(int yellowCardsOld) {
        this.yellowCardsOld = yellowCardsOld;
    }

    public int getYellowCardsNew() {
        return yellowCardsNew;
    }

    public void setYellowCardsNew(int yellowCardsNew) {
        this.yellowCardsNew = yellowCardsNew;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public int getRedCardsOld() {
        return redCardsOld;
    }

    public void setRedCardsOld(int redCardsOld) {
        this.redCardsOld = redCardsOld;
    }

    public int getRedCardsNew() {
        return redCardsNew;
    }

    public void setRedCardsNew(int redCardsNew) {
        this.redCardsNew = redCardsNew;
    }

    @NonNull
    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(@NonNull ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }

    public int getGames26() {
        return games26;
    }

    public void setGames26(int games26) {
        this.games26 = games26;
    }

    public int getGames54() {
        return games54;
    }

    public void setGames54(int games54) {
        this.games54 = games54;
    }

    public int getGamesFriendly() {
        return gamesFriendly;
    }

    public void setGamesFriendly(int gamesFriendly) {
        this.gamesFriendly = gamesFriendly;
    }

    public int getGoals26() {
        return goals26;
    }

    public void setGoals26(int goals26) {
        this.goals26 = goals26;
    }

    public int getGoals54() {
        return goals54;
    }

    public void setGoals54(int goals54) {
        this.goals54 = goals54;
    }

    public int getGoalsFriendly() {
        return goalsFriendly;
    }

    public void setGoalsFriendly(int goalsFriendly) {
        this.goalsFriendly = goalsFriendly;
    }

    public int getAssists26() {
        return assists26;
    }

    public void setAssists26(int assists26) {
        this.assists26 = assists26;
    }

    public int getAssists54() {
        return assists54;
    }

    public void setAssists54(int assists54) {
        this.assists54 = assists54;
    }

    public int getAssistsFriendly() {
        return assistsFriendly;
    }

    public void setAssistsFriendly(int assistsFriendly) {
        this.assistsFriendly = assistsFriendly;
    }

    public int getPoints26() {
        return points26;
    }

    public void setPoints26(int points26) {
        this.points26 = points26;
    }

    public int getPoints54() {
        return points54;
    }

    public void setPoints54(int points54) {
        this.points54 = points54;
    }

    public int getPointsFriendly() {
        return pointsFriendly;
    }

    public void setPointsFriendly(int pointsFriendly) {
        this.pointsFriendly = pointsFriendly;
    }
}

