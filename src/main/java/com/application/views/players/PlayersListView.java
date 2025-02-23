package com.application.views.players;

import com.application.model.Player;
import com.application.service.PlayerService;
import com.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

/**
 * Table for tab with {@link Player}s
 *
 * @author Ilya Ryabukhin
 * @since 18.04.2023
 */
@Route(value = "players", layout = MainLayout.class)
@PageTitle("Игроки | Н26/54 статистика")
public class PlayersListView extends VerticalLayout {
    Grid<Player> grid = new Grid<>(Player.class);
    TextField filterText = new TextField();
    PlayerService playerService;

    public PlayersListView(PlayerService playerService) {
        this.playerService = playerService;
        addClassName("players-list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), getContent());
        updateList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassNames("players-list-content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassNames("players-grid");
        grid.setSizeFull();
        grid.setColumns("name", "games", "games26", "games54", "gamesFriendly", "goals", "goals26",
                "goals54", "goalsFriendly", "assists", "assists26", "assists54", "assistsFriendly", "points", "points26",
                "points54", "pointsFriendly", "yellowCards", "redCards");
        grid.getColumnByKey("name").setHeader("Имя").setFrozen(true);

        grid.getColumnByKey("games26").setHeader("Игр Н26");
        grid.getColumnByKey("games54").setHeader("Игр Н54");
        grid.getColumnByKey("gamesFriendly").setHeader("Игр (товар.)");
        grid.getColumnByKey("games").setHeader("Игр (всего)");

        grid.getColumnByKey("goals26").setHeader("Голов Н26");
        grid.getColumnByKey("goals54").setHeader("Голов Н54");
        grid.getColumnByKey("goalsFriendly").setHeader("Голов (товар.)");
        grid.getColumnByKey("goals").setHeader("Голов (всего)");

        grid.getColumnByKey("assists26").setHeader("Передач Н26");
        grid.getColumnByKey("assists54").setHeader("Передач Н54");
        grid.getColumnByKey("assistsFriendly").setHeader("Передач (товар.)");
        grid.getColumnByKey("assists").setHeader("Передач (всего)");

        grid.getColumnByKey("points26").setHeader("Очков Н26");
        grid.getColumnByKey("points54").setHeader("Очков Н54");
        grid.getColumnByKey("pointsFriendly").setHeader("Очков (товар.)");
        grid.getColumnByKey("points").setHeader("Очков (всего)");

        grid.getColumnByKey("yellowCards").setHeader("ЖК");
        grid.getColumnByKey("redCards").setHeader("КК");

        grid.addColumn(player -> player.getActivityStatus().getTextualStatus()).setHeader("Статус").setKey("activityStatus");
        grid.addColumn(game -> "").setKey("rowIndex");
        grid.addAttachListener(event -> grid.getColumnByKey("rowIndex").getElement().executeJs(
                "this.renderer = function(root, column, rowData) {root.textContent = rowData.index + 1}"
        ));

        grid.setColumnOrder(
                grid.getColumnByKey("rowIndex"),
                grid.getColumnByKey("name"),
                grid.getColumnByKey("games26"),
                grid.getColumnByKey("games54"),
                grid.getColumnByKey("gamesFriendly"),
                grid.getColumnByKey("games"),
                grid.getColumnByKey("goals26"),
                grid.getColumnByKey("goals54"),
                grid.getColumnByKey("goalsFriendly"),
                grid.getColumnByKey("goals"),
                grid.getColumnByKey("assists26"),
                grid.getColumnByKey("assists54"),
                grid.getColumnByKey("assistsFriendly"),
                grid.getColumnByKey("assists"),
                grid.getColumnByKey("points26"),
                grid.getColumnByKey("points54"),
                grid.getColumnByKey("pointsFriendly"),
                grid.getColumnByKey("points"),
                grid.getColumnByKey("yellowCards"),
                grid.getColumnByKey("redCards"),
                grid.getColumnByKey("activityStatus")
        );

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumnByKey("rowIndex").setAutoWidth(false).setWidth("4em");

        grid.sort(List.of(new GridSortOrder<>(grid.getColumnByKey("games"), SortDirection.DESCENDING)));

    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Отфильтровать по имени ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        filterText.setWidth("400px");

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("players-toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(playerService.findAllPlayersByName(filterText.getValue()));
    }

}
