package com.application.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

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

}
