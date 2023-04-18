package com.application.views.teams;

import com.application.model.Principal;
import com.application.model.Team;
import com.application.service.TeamService;
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

@Route(value = "teams", layout = MainLayout.class)
@PageTitle("Команды | Н26/54 статистика")
public class TeamsListView extends VerticalLayout {
    Grid<Team> grid = new Grid<>(Team.class);
    TextField filterText = new TextField();
    TeamForm form;
    TeamService teamService;

    public TeamsListView(TeamService teamService) {
        this.teamService = teamService;
        addClassName("teams-list-view");
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
        content.addClassNames("teams-list-content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new TeamForm(Arrays.asList(Team.GameSide.values()));
        form.setWidth("25em");

        form.addListener(TeamForm.SaveEvent.class, this::saveTeam);
        form.addListener(TeamForm.DeleteEvent.class, this::deleteTeam);
        form.addListener(TeamForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("teams-grid");
        grid.setSizeFull();
        grid.setColumns("name", "created", "edited");
        grid.getColumnByKey("name").setHeader("Название");
        grid.addColumn(new LocalDateRenderer<>(Team::getBirthday, "dd-MM-yyyy")).setHeader("Дата основания").setKey("birthday");
        grid.addColumn(team -> team.getSide().getSideName()).setHeader("Сторона").setKey("side");
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
                grid.getColumnByKey("name"),
                grid.getColumnByKey("side"),
                grid.getColumnByKey("birthday"),
                grid.getColumnByKey("created"),
                grid.getColumnByKey("creator"),
                grid.getColumnByKey("edited"),
                grid.getColumnByKey("editor")
        );

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editTeam(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Отфильтровать по названию ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        filterText.setWidth("400px");

        Button addTeamButton = new Button("Добавить команду");
        addTeamButton.addClickListener(click -> addTeam());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addTeamButton);
        toolbar.addClassName("teams-toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(teamService.findAllTeams(filterText.getValue()));
    }

    private void addTeam() {
        grid.asSingleSelect().clear();
        editTeam(new Team());
    }

    public void editTeam(Team team) {
        if (team == null) {
            closeEditor();
        } else {
            form.setTeam(team);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveTeam(TeamForm.SaveEvent event) {
        teamService.saveTeam(event.getTeam());
        updateList();
        closeEditor();
    }

    private void deleteTeam(TeamForm.DeleteEvent event) {
        teamService.deleteTeam(event.getTeam());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setTeam(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
