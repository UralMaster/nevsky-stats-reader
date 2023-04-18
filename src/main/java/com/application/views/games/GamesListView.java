package com.application.views.games;

import com.application.model.Game;
import com.application.model.Principal;
import com.application.service.*;
import com.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "games", layout = MainLayout.class)
@PageTitle("Игры | Н26/54 статистика")
public class GamesListView extends VerticalLayout {
    Grid<Game> grid = new Grid<>(Game.class);
    GameDialog dialog;
    GameService gameService;
    TeamService teamService;
    TournamentService tournamentService;
    SeasonService seasonService;
    ParticipanceService participanceService;
    PlayerService playerService;

    public GamesListView(GameService gameService,
                         TeamService teamService,
                         TournamentService tournamentService,
                         SeasonService seasonService,
                         ParticipanceService participanceService,
                         PlayerService playerService)
    {
        this.gameService = gameService;
        this.teamService = teamService;
        this.tournamentService = tournamentService;
        this.seasonService = seasonService;
        this.participanceService = participanceService;
        this.playerService = playerService;

        addClassName("games-list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), getContent());
        updateList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassNames("games-list-content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassNames("games-grid");
        grid.setSizeFull();
        grid.setColumns("created", "edited", "nevskyGoals", "oppositeGoals");
        grid.addColumn(new LocalDateRenderer<>(Game::getGamedate, "dd-MM-yyyy")).setHeader("Дата").setKey("gamedate")
                .setSortable(true)
                .setComparator(Game::getGamedate);
        grid.addColumn(game -> game.getNevskyTeam().getName()).setHeader("Команда Невского").setKey("nevskyTeam");
        grid.getColumnByKey("nevskyGoals").setHeader("Забито");
        grid.getColumnByKey("oppositeGoals").setHeader("Пропущено");
        grid.addColumn(game -> game.getOppositeTeam().getName()).setHeader("Соперник").setKey("oppositeTeam");
        grid.addColumn(game -> game.getTournament().getName()).setHeader("Турнир").setKey("tournament");
        grid.addColumn(game -> game.getSeason().getName()).setHeader("Сезон").setKey("season");
        grid.addColumn(game -> game.getHistoricalStatus().getTextualStatus()).setHeader("Статус").setKey("historicalStatus");

        grid.getColumnByKey("created").setHeader("Создана (в системе)");
        grid.addColumn(team -> team.getCreator().getUsername()).setHeader("Создатель (в системе)").setKey("creator");
        grid.getColumnByKey("edited").setHeader("Отредактирована");
        grid.addColumn(team -> {
            Principal editor = team.getEditor();
            if (editor == null) {
                return "";
            } else {
                return editor.getUsername();
            }
        }).setHeader("Редактор").setKey("editor");
        grid.setColumnOrder(
                grid.getColumnByKey("gamedate"),
                grid.getColumnByKey("nevskyTeam"),
                grid.getColumnByKey("nevskyGoals"),
                grid.getColumnByKey("oppositeGoals"),
                grid.getColumnByKey("oppositeTeam"),
                grid.getColumnByKey("tournament"),
                grid.getColumnByKey("season"),
                grid.getColumnByKey("historicalStatus"),
                grid.getColumnByKey("created"),
                grid.getColumnByKey("creator"),
                grid.getColumnByKey("edited"),
                grid.getColumnByKey("editor")
        );

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.sort(List.of(new GridSortOrder<>(grid.getColumnByKey("gamedate"), SortDirection.DESCENDING)));

        grid.asSingleSelect().addValueChangeListener(event ->
                editGame(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        Button addGameButton = new Button("Добавить игру");
        addGameButton.addClickListener(click -> addGame());

        HorizontalLayout toolbar = new HorizontalLayout(addGameButton);
        toolbar.addClassName("games-toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(gameService.findAllGames());
    }

    private void addGame() {
        grid.asSingleSelect().clear();
        editGame(new Game());
    }

    public void editGame(Game game) {
        if (game != null) {
            dialog = new GameDialog(teamService, tournamentService, seasonService, participanceService, playerService, game);
            dialog.addListener(GameDialog.SaveEvent.class, this::saveGame);
            dialog.addListener(GameDialog.CloseEvent.class, e -> closeDialog());
            dialog.setHeight("100%");
            dialog.open();
        }
    }

    private void saveGame(GameDialog.SaveEvent event) {
        gameService.saveGame(event.getGame());
        updateList();
        dialog.close();
    }

    private void closeDialog() {
        dialog.close();
    }

}
