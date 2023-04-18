package com.application.views.players;

import com.application.model.Player;
import com.application.model.Principal;
import com.application.service.PlayerService;
import com.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@Route(value = "players", layout = MainLayout.class)
@PageTitle("Игроки | Н26/54 статистика")
public class PlayersListView extends VerticalLayout {
    Grid<Player> grid = new Grid<>(Player.class);
    TextField filterText = new TextField();
    PlayerForm form;
    PlayerService playerService;

    public PlayersListView(PlayerService playerService) {
        this.playerService = playerService;
        addClassName("players-list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("players-list-content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new PlayerForm(Arrays.asList(Player.ActivityStatus.values()));
        form.setWidth("25em");

        form.addListener(PlayerForm.SaveEvent.class, this::savePlayer);
        form.addListener(PlayerForm.DeleteEvent.class, this::deletePlayer);
        form.addListener(PlayerForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("players-grid");
        grid.setSizeFull();
        grid.setColumns("name", "created", "edited", "games", "games26", "games54", "gamesFriendly", "goals", "goals26",
                "goals54", "goalsFriendly", "assists", "assists26", "assists54", "assistsFriendly", "points", "points26",
                "points54", "pointsFriendly", "yellowCards", "redCards");
        grid.getColumnByKey("name").setHeader("Имя").setFrozen(true);
        grid.addColumn(new LocalDateRenderer<>(Player::getBirthday, "dd-MM-yyyy")).setHeader("День рождения").setKey("birthday");

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

        grid.getColumnByKey("created").setHeader("Создан (в системе)");
        grid.addColumn(team -> team.getCreator().getUsername()).setHeader("Создатель (в системе)").setKey("creator");
        grid.getColumnByKey("edited").setHeader("Отредактирован");
        grid.addColumn(player -> {
            Principal editor = player.getEditor();
            if (editor == null) {
                return "";
            } else {
                return editor.getUsername();
            }
        }).setHeader("Редактор").setKey("editor");
        grid.setColumnOrder(
                grid.getColumnByKey("name"),
                grid.getColumnByKey("birthday"),
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
                grid.getColumnByKey("activityStatus"),
                grid.getColumnByKey("created"),
                grid.getColumnByKey("creator"),
                grid.getColumnByKey("edited"),
                grid.getColumnByKey("editor")
        );

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.sort(List.of(new GridSortOrder<>(grid.getColumnByKey("games"), SortDirection.DESCENDING)));

        grid.asSingleSelect().addValueChangeListener(event ->
                editPlayer(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Отфильтровать по имени ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        filterText.setWidth("400px");

        Button addPlayerButton = new Button("Добавить игрока");
        addPlayerButton.addClickListener(click -> addPlayer());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPlayerButton);
        toolbar.addClassName("players-toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(playerService.findAllPlayers(filterText.getValue()));
    }

    private void addPlayer() {
        grid.asSingleSelect().clear();
        editPlayer(new Player());
    }

    public void editPlayer(Player player) {
        if (player == null) {
            closeEditor();
        } else {
            form.setPlayer(player);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void savePlayer(PlayerForm.SaveEvent event) {
        playerService.savePlayer(event.getPlayer());
        updateList();
        closeEditor();
    }

    private void deletePlayer(PlayerForm.DeleteEvent event) {
        playerService.deletePlayer(event.getPlayer());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setPlayer(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
