package com.application.views.games;

import com.application.model.Game;
import com.application.model.Participance;
import com.application.service.ParticipanceService;
import com.application.service.PlayerService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Table for tab with {@link Participance}s
 *
 * @author Ilya Ryabukhin
 * @since 18.04.2023
 */
public class ParticipanceListView extends VerticalLayout {
    Grid<Participance> grid = new Grid<>(Participance.class);
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

        add(getContent());
        if (game.getId() != null) {
            updateList(game);
        }
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassNames("participants-list-content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassNames("participance-grid");
        grid.setSizeFull();
        grid.setColumns("goals", "assists", "yellowCards", "redCards");
        grid.addColumn(participance -> participance.getPlayer().getName()).setHeader("Игрок").setKey("player");
        grid.addColumn(participance -> "").setKey("rowIndex");
        grid.addAttachListener(event -> grid.getColumnByKey("rowIndex").getElement().executeJs(
                "this.renderer = function(root, column, rowData) {root.textContent = rowData.index + 1}"
        ));
        grid.getColumnByKey("goals").setHeader("Голов");
        grid.getColumnByKey("assists").setHeader("Передач");
        grid.getColumnByKey("yellowCards").setHeader("ЖК");
        grid.getColumnByKey("redCards").setHeader("КК");

        grid.setColumnOrder(
                grid.getColumnByKey("rowIndex"),
                grid.getColumnByKey("player"),
                grid.getColumnByKey("goals"),
                grid.getColumnByKey("assists"),
                grid.getColumnByKey("yellowCards"),
                grid.getColumnByKey("redCards")
        );

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumnByKey("rowIndex").setAutoWidth(false).setWidth("4em");

    }

    private void updateList(Game game) {
        grid.setItems(participanceService.findParticipanceByGame(game));
    }

}
