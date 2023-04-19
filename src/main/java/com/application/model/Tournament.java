package com.application.model;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

/**
 * Contains main info about tournament.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
@Entity
public class Tournament extends AbstractEntity {

    @NotEmpty
    private String name = "";

    /**
     * Constructor
     */
    public Tournament() {
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

}

