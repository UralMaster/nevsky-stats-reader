package com.application.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

/**
 * Contains main info about particular player's participance in particular game.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
@Entity
public class Participance extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game")
    private Game game;

    private int goals;

    private int assists;

    @Column(name = "yellow_cards")
    private int yellowCards;

    @Column(name = "red_cards")
    private int redCards;

    /**
     * Constructor
     */
    public Participance() {
    }

    @NonNull
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(@NonNull Player player) {
        this.player = player;
    }

    @NonNull
    public Game getGame() {
        return game;
    }

    public void setGame(@NonNull Game game) {
        this.game = game;
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

}

