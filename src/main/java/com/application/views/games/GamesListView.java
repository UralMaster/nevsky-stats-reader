package com.application.views.games;

import com.application.model.Game;
import com.application.service.GameService;
import com.application.service.ParticipanceService;
import com.application.service.PlayerService;
import com.application.service.SeasonService;
import com.application.service.TeamService;
import com.application.service.TournamentService;
import com.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

/**
 * Table for tab with {@link Game}s
 *
 * @author Ilya Ryabukhin
 * @since 18.04.2023
 */
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

        add(getContent());
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
        grid.setColumns("nevskyGoals", "oppositeGoals");
        grid.addColumn(new LocalDateRenderer<>(Game::getGamedate, "dd-MM-yyyy")).setHeader("Дата").setKey("gamedate")
                .setSortable(true)
                .setComparator(Game::getGamedate);
        grid.addColumn(game -> game.getNevskyTeam().getName()).setHeader("Команда Невского").setKey("nevskyTeam");
        grid.addColumn(game -> "").setKey("rowIndex");
        grid.addAttachListener(event -> grid.getColumnByKey("rowIndex").getElement().executeJs(
                "this.renderer = function(root, column, rowData) {root.textContent = rowData.index + 1}"
        ));
        grid.getColumnByKey("nevskyGoals").setHeader("Забито");
        grid.getColumnByKey("oppositeGoals").setHeader("Пропущено");
        grid.addColumn(game -> game.getOppositeTeam().getName()).setHeader("Соперник").setKey("oppositeTeam");
        grid.addColumn(game -> game.getTournament().getName()).setHeader("Турнир").setKey("tournament");
        grid.addColumn(game -> game.getSeason().getName()).setHeader("Сезон").setKey("season");

        grid.setColumnOrder(
                grid.getColumnByKey("rowIndex"),
                grid.getColumnByKey("gamedate"),
                grid.getColumnByKey("nevskyTeam"),
                grid.getColumnByKey("nevskyGoals"),
                grid.getColumnByKey("oppositeGoals"),
                grid.getColumnByKey("oppositeTeam"),
                grid.getColumnByKey("tournament"),
                grid.getColumnByKey("season")
        );

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumnByKey("rowIndex").setAutoWidth(false).setWidth("4em");

        grid.sort(List.of(new GridSortOrder<>(grid.getColumnByKey("gamedate"), SortDirection.DESCENDING)));

        grid.asSingleSelect().addValueChangeListener(event ->
                openGame(event.getValue()));
    }

    private void updateList() {
        grid.setItems(gameService.findAllGames());
    }

    public void openGame(Game game) {
        if (game != null) {
            dialog = new GameDialog(teamService, tournamentService, seasonService, participanceService, playerService, game);
            dialog.addListener(GameDialog.CloseEvent.class, e -> closeDialog());
            dialog.setHeight("100%");
            dialog.open();
        }
    }

    private void closeDialog() {
        dialog.close();
    }

}
