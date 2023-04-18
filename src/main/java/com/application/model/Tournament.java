package com.application.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

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
    public Tournament() {
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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

