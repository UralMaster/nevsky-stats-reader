package com.application.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

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
    private LocalDate birthday;

    private int games;
    @Column(name = "games_26")
    private int games26;
    @Column(name = "games_54")
    private int games54;
    @Column(name = "games_friendly")
    private int gamesFriendly;

    private int goals;
    @Column(name = "goals_26")
    private int goals26;
    @Column(name = "goals_54")
    private int goals54;
    @Column(name = "goals_friendly")
    private int goalsFriendly;

    private int assists;
    @Column(name = "assists_26")
    private int assists26;
    @Column(name = "assists_54")
    private int assists54;
    @Column(name = "assists_friendly")
    private int assistsFriendly;

    private int points;
    @Column(name = "points_26")
    private int points26;
    @Column(name = "points_54")
    private int points54;
    @Column(name = "points_friendly")
    private int pointsFriendly;

    @Column(name = "yellow_cards")
    private int yellowCards;
    @Column(name = "red_cards")
    private int redCards;

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

    /**
     * @return birthday if it's present, otherwise - null
     */
    @Nullable
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday
     *
     * @param birthday for setting
     */
    public void setBirthday(@Nullable LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
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

    /**
     * Simplifies string representation of {@link Player} for logging purposes
     *
     * @return simplified string representation of {@link Player}
     */
    @Override
    public String toString() {
        return name;
    }

}
