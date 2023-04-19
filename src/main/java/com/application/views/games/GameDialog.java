package com.application.views.games;

import com.application.model.*;
import com.application.service.*;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.stream.Collectors;

public class GameDialog extends Dialog {
    ComboBox<Team> nevskyTeam = new ComboBox<>("Невский");
    ComboBox<Team> oppositeTeam = new ComboBox<>("Соперник");
    IntegerField nevskyGoals = new IntegerField("Голы Невского");
    IntegerField oppositeGoals = new IntegerField("Голы соперника");
    DatePicker gamedate = new DatePicker("Дата игры");
    ComboBox<Tournament> tournament = new ComboBox<>("Турнир");
    ComboBox<Season> season = new ComboBox<>("Сезон");

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
        nevskyTeam.setReadOnly(true);
        oppositeTeam.setItems(teamService.findAllTeams(null).stream()
                .filter(team -> team.getSide() == Team.GameSide.OTHER)
                .collect(Collectors.toList()));
        oppositeTeam.setItemLabelGenerator(Team::getName);
        oppositeTeam.setReadOnly(true);

        nevskyGoals.setMin(0);
        nevskyGoals.setReadOnly(true);
        oppositeGoals.setMin(0);
        oppositeGoals.setReadOnly(true);

        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd-MM-yyyy");
        gamedate.setI18n(singleFormatI18n);
        gamedate.setPlaceholder("dd-MM-yyyy");
        gamedate.setReadOnly(true);

        tournament.setItems(tournamentService.findAllTournaments(null));
        tournament.setItemLabelGenerator(Tournament::getName);
        tournament.setReadOnly(true);
        season.setItems(seasonService.findAllSeasons(null));
        season.setItemLabelGenerator(Season::getName);
        season.setReadOnly(true);

        FormLayout formLayout = new FormLayout(nevskyTeam,
                oppositeTeam,
                nevskyGoals,
                oppositeGoals,
                gamedate,
                tournament,
                season);
        binder.readBean(game);
        add(formLayout, new ParticipanceListView(participanceService, playerService, game));
    }

    public void setGame(Game game) {
        this.game = game;
        binder.readBean(game);
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
