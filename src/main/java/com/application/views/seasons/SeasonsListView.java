package com.application.views.seasons;

import com.application.model.Principal;
import com.application.model.Season;
import com.application.service.SeasonService;
import com.application.service.TournamentService;
import com.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;

@Route(value = "seasons", layout = MainLayout.class)
@PageTitle("Сезоны | Н26/54 статистика")
public class SeasonsListView extends VerticalLayout {
    Grid<Season> grid = new Grid<>(Season.class);
    TextField filterText = new TextField();
    SeasonForm form;
    SeasonService seasonService;
    TournamentService tournamentService;

    public SeasonsListView(SeasonService seasonService, TournamentService tournamentService) {
        this.seasonService = seasonService;
        this.tournamentService = tournamentService;
        addClassName("seasons-list-view");
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
        content.addClassNames("seasons-list-content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new SeasonForm(Arrays.asList(Season.Division.values()), tournamentService.findAllTournaments(null));
        form.setWidth("25em");

        form.addListener(SeasonForm.SaveEvent.class, this::saveSeason);
        form.addListener(SeasonForm.DeleteEvent.class, this::deleteSeason);
        form.addListener(SeasonForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("seasons-grid");
        grid.setSizeFull();
        grid.setColumns("name", "created", "edited");
        grid.addColumn(season -> season.getTournament().getName()).setHeader("Турнир").setKey("tournament");
        grid.addColumn(season -> season.getDivision().getDivisionName()).setHeader("Дивизион").setKey("division");
        grid.addColumn(new LocalDateRenderer<>(Season::getStartDate, "dd-MM-yyyy")).setHeader("Дата начала").setKey("startDate");
        grid.addColumn(new LocalDateRenderer<>(Season::getEndDate, "dd-MM-yyyy")).setHeader("Дата окончания").setKey("endDate");
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
                grid.getColumnByKey("tournament"),
                grid.getColumnByKey("division"),
                grid.getColumnByKey("name"),
                grid.getColumnByKey("startDate"),
                grid.getColumnByKey("endDate"),
                grid.getColumnByKey("created"),
                grid.getColumnByKey("creator"),
                grid.getColumnByKey("edited"),
                grid.getColumnByKey("editor")
        );

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editSeason(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Отфильтровать по названию ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        filterText.setWidth("400px");

        Button addSeasonButton = new Button("Добавить сезон");
        addSeasonButton.addClickListener(click -> addSeason());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addSeasonButton);
        toolbar.addClassName("seasons-toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(seasonService.findAllSeasons(filterText.getValue()));
    }

    private void addSeason() {
        grid.asSingleSelect().clear();
        editSeason(new Season());
    }

    public void editSeason(Season season) {
        if (season == null) {
            closeEditor();
        } else {
            form.setSeason(season);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveSeason(SeasonForm.SaveEvent event) {
        seasonService.saveSeason(event.getSeason());
        updateList();
        closeEditor();
    }

    private void deleteSeason(SeasonForm.DeleteEvent event) {
        seasonService.deleteSeason(event.getSeason());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setSeason(null);
        form.setVisible(false);
        removeClassName("editing");
    }

}
