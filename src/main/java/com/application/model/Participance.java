package com.application.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

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

}

