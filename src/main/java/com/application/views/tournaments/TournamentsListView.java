package com.application.views.tournaments;

import com.application.model.Principal;
import com.application.model.Tournament;
import com.application.service.TournamentService;
import com.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "tournaments", layout = MainLayout.class)
@PageTitle("Турниры | Н26/54 статистика")
public class TournamentsListView extends VerticalLayout {
    Grid<Tournament> grid = new Grid<>(Tournament.class);
    TextField filterText = new TextField();
    TournamentForm form;
    TournamentService tournamentService;

    public TournamentsListView(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
        addClassName("tournaments-list-view");
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
        content.addClassNames("tournaments-list-content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new TournamentForm();
        form.setWidth("25em");

        form.addListener(TournamentForm.SaveEvent.class, this::saveTournament);
        form.addListener(TournamentForm.DeleteEvent.class, this::deleteTournament);
        form.addListener(TournamentForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("tournaments-grid");
        grid.setSizeFull();
        grid.setColumns("name", "created", "edited");
        grid.getColumnByKey("name").setHeader("Название");
        grid.getColumnByKey("created").setHeader("Создан (в системе)");
        grid.addColumn(team -> team.getCreator().getUsername()).setHeader("Создатель (в системе)").setKey("creator");
        grid.getColumnByKey("edited").setHeader("Отредактирован");
        grid.addColumn(team -> {
            Principal editor = team.getEditor();
            if (editor == null) {
                return "";
            } else {
                return editor.getUsername();
            }
        }).setHeader("Редактор").setKey("editor");
        grid.setColumnOrder(
                grid.getColumnByKey("name"),
                grid.getColumnByKey("created"),
                grid.getColumnByKey("creator"),
                grid.getColumnByKey("edited"),
                grid.getColumnByKey("editor")
        );

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editTournament(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Отфильтровать по названию ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        filterText.setWidth("400px");

        Button addTournamentButton = new Button("Добавить турнир");
        addTournamentButton.addClickListener(click -> addTournament());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addTournamentButton);
        toolbar.addClassName("tournaments-toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(tournamentService.findAllTournaments(filterText.getValue()));
    }

    private void addTournament() {
        grid.asSingleSelect().clear();
        editTournament(new Tournament());
    }

    public void editTournament(Tournament tournament) {
        if (tournament == null) {
            closeEditor();
        } else {
            form.setTournament(tournament);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveTournament(TournamentForm.SaveEvent event) {
        tournamentService.saveTournament(event.getTournament());
        updateList();
        closeEditor();
    }

    private void deleteTournament(TournamentForm.DeleteEvent event) {
        tournamentService.deleteTournament(event.getTournament());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setTournament(null);
        form.setVisible(false);
        removeClassName("editing");
    }

}
