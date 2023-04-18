package com.application.views.games;

import com.application.model.*;
import com.application.service.*;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GameDialog extends Dialog {
    ComboBox<Team> nevskyTeam = new ComboBox<>("Невский");
    ComboBox<Team> oppositeTeam = new ComboBox<>("Соперник");
    IntegerField nevskyGoals = new IntegerField("Голы Невского");
    IntegerField oppositeGoals = new IntegerField("Голы соперника");
    DatePicker gamedate = new DatePicker("Дата игры");
    ComboBox<Tournament> tournament = new ComboBox<>("Турнир");
    ComboBox<Season> season = new ComboBox<>("Сезон");
    ComboBox<Game.HistoricalStatus> historicalStatus = new ComboBox<>("Статус");

    Button save = new Button("Сохранить");
    Button close = new Button("Отмена");

    Binder<Game> binder = new BeanValidationBinder<>(Game.class);

    private Game game;

    public GameDialog(TeamService teamService,
                      TournamentService tournamentService,
                      SeasonService seasonService,
                      ParticipanceService participanceService,
                      PlayerService playerService,
                      Game game)
    {
        this.game = game;

        binder.bindInstanceFields(this);

        nevskyTeam.setItems(teamService.findAllTeams(null).stream()
                .filter(team -> team.getSide() == Team.GameSide.NEVSKY)
                .collect(Collectors.toList()));
        nevskyTeam.setItemLabelGenerator(Team::getName);
        oppositeTeam.setItems(teamService.findAllTeams(null).stream()
                .filter(team -> team.getSide() == Team.GameSide.OTHER)
                .collect(Collectors.toList()));
        oppositeTeam.setItemLabelGenerator(Team::getName);

        nevskyGoals.setMin(0);
        oppositeGoals.setMin(0);

        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd-MM-yyyy");
        gamedate.setI18n(singleFormatI18n);
        gamedate.setPlaceholder("dd-MM-yyyy");

        tournament.setItems(tournamentService.findAllTournaments(null));
        tournament.setItemLabelGenerator(Tournament::getName);
        season.setItems(seasonService.findAllSeasons(null));
        season.setItemLabelGenerator(Season::getName);

        historicalStatus.setItems(Arrays.asList(Game.HistoricalStatus.values()));
        historicalStatus.setItemLabelGenerator(Game.HistoricalStatus::getTextualStatus);

        FormLayout formLayout = new FormLayout(nevskyTeam,
                oppositeTeam,
                nevskyGoals,
                oppositeGoals,
                gamedate,
                tournament,
                season,
                historicalStatus,
                createButtonsLayout());
        binder.readBean(game);
        add(formLayout, new ParticipanceListView(participanceService, playerService, game));
    }

    public HorizontalLayout createButtonsLayout() {

        save.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(close, save);
    }

    public void setGame(Game game) {
        this.game = game;
        binder.readBean(game);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(game);
            fireEvent(new SaveEvent(this, game) );
        } catch (ValidationException e) {
            //TODO: logging
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class GameDialogEvent extends ComponentEvent<GameDialog> {
        private final Game game;

        protected GameDialogEvent(GameDialog source, Game game) {
            super(source, false);
            this.game = game;
        }

        public Game getGame() {
            return game;
        }
    }

    public static class SaveEvent extends GameDialogEvent {
        SaveEvent(GameDialog source, Game game) {
            super(source, game);
        }
    }

    public static class CloseEvent extends GameDialogEvent {
        CloseEvent(GameDialog source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
