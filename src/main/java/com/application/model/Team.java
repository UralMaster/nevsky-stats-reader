package com.application.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Contains main info about team.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
@Entity
public class Team extends AbstractEntity {

    /**
     * Enumeration with game sides
     */
    public enum GameSide {
        NEVSKY("Невский"),
        OTHER("Соперник");

        private final String sideName;

        /**
         * Constructor
         *
         * @param sideName textual name of game side
         */
        GameSide(@NonNull String sideName) {
            this.sideName = sideName;
        }

        @NonNull
        public String getSideName() {
            return sideName;
        }

    }

    @NotEmpty
    private String name = "";

    @Enumerated(EnumType.STRING)
    private GameSide side;

    private LocalDate birthday;

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
    public Team() {
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public GameSide getSide() {
        return side;
    }

    public void setSide(@NonNull GameSide side) {
        this.side = side;
    }

    @Nullable
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(@Nullable LocalDate birthday) {
        this.birthday = birthday;
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
