package com.application.views.games;

import com.application.model.Game;
import com.application.model.Participance;
import com.application.model.Principal;
import com.application.service.ParticipanceService;
import com.application.service.PlayerService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.UUID;

public class ParticipanceListView extends VerticalLayout {
    Grid<Participance> grid = new Grid<>(Participance.class);
    ParticipanceForm form;
    ParticipanceService participanceService;
    PlayerService playerService;
    Game game;

    public ParticipanceListView(ParticipanceService participanceService, PlayerService playerService, Game game) {
        this.participanceService = participanceService;
        this.playerService = playerService;
        this.game = game;
        addClassName("participance-list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        if (game.getId() != null) {
            updateList(game);
        }
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("participants-list-content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new ParticipanceForm(playerService.findAllPlayers(null));
        form.setWidth("25em");

        form.addListener(ParticipanceForm.SaveEvent.class, this::saveParticipance);
        form.addListener(ParticipanceForm.DeleteEvent.class, this::deleteParticipance);
        form.addListener(ParticipanceForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("participance-grid");
        grid.setSizeFull();
        grid.setColumns("created", "edited", "goals", "assists", "yellowCards", "redCards");
        grid.addColumn(participance -> participance.getPlayer().getName()).setHeader("Игрок").setKey("player");
        grid.getColumnByKey("goals").setHeader("Голов");
        grid.getColumnByKey("assists").setHeader("Передач");
        grid.getColumnByKey("yellowCards").setHeader("ЖК");
        grid.getColumnByKey("redCards").setHeader("КК");

        grid.getColumnByKey("created").setHeader("Создан (в системе)");
        grid.addColumn(season -> season.getCreator().getUsername()).setHeader("Создатель (в системе)").setKey("creator");
        grid.getColumnByKey("edited").setHeader("Отредактирован");
        grid.addColumn(season -> {
            Principal editor = season.getEditor();
            if (editor == null) {
                return "";
            } else {
                return editor.getUsername();
            }
        }).setHeader("Редактор").setKey("editor");
        grid.setColumnOrder(
                grid.getColumnByKey("player"),
                grid.getColumnByKey("goals"),
                grid.getColumnByKey("assists"),
                grid.getColumnByKey("yellowCards"),
                grid.getColumnByKey("redCards"),
                grid.getColumnByKey("created"),
                grid.getColumnByKey("creator"),
                grid.getColumnByKey("edited"),
                grid.getColumnByKey("editor")
        );

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editParticipance(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        Button addParticipanceButton = new Button("Добавить игрока");
        addParticipanceButton.addClickListener(click -> addParticipance());

        HorizontalLayout toolbar = new HorizontalLayout(addParticipanceButton);
        toolbar.addClassName("participance-toolbar");
        return toolbar;
    }

    private void updateList(Game game) {
        grid.setItems(participanceService.findParticipanceByGame(game));
    }

    private void addParticipance() {
        grid.asSingleSelect().clear();
        Participance participance = new Participance();
        participance.setGame(game);
        participance.setId(UUID.randomUUID());
        editParticipance(participance);
    }

    public void editParticipance(Participance participance) {
        if (participance == null) {
            closeEditor();
        } else {
            form.setParticipance(participance);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveParticipance(ParticipanceForm.SaveEvent event) {
        participanceService.saveParticipance(event.getParticipance());
        updateList(game);
        closeEditor();
    }

    private void deleteParticipance(ParticipanceForm.DeleteEvent event) {
        participanceService.deleteParticipance(event.getParticipance());
        updateList(game);
        closeEditor();
    }

    private void closeEditor() {
        form.setParticipance(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
