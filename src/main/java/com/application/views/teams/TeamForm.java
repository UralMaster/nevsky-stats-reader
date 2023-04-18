package com.application.views.teams;

import com.application.model.Team;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class TeamForm extends FormLayout {
    TextField name = new TextField("Название");
    ComboBox<Team.GameSide> side = new ComboBox<>("Сторона");
    DatePicker birthday = new DatePicker("Дата основания");

    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");

    Binder<Team> binder = new BeanValidationBinder<>(Team.class);

    private Team team;

    public TeamForm(List<Team.GameSide> sides) {
        addClassName("team-form");
        binder.bindInstanceFields(this);

        side.setItems(sides);
        side.setItemLabelGenerator(Team.GameSide::getSideName);

        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd-MM-yyyy");
        birthday.setI18n(singleFormatI18n);
        birthday.setPlaceholder("dd-MM-yyyy");

        add(name,
            side,
            birthday,
            createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, team)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setTeam(Team team) {
        this.team = team;
        binder.readBean(team);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(team);
            fireEvent(new SaveEvent(this, team));
        } catch (ValidationException e) {
            //TODO: logging
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class TeamFormEvent extends ComponentEvent<TeamForm> {
        private Team team;

        protected TeamFormEvent(TeamForm source, Team team) {
            super(source, false);
            this.team = team;
        }

        public Team getTeam() {
            return team;
        }
    }

    public static class SaveEvent extends TeamFormEvent {
        SaveEvent(TeamForm source, Team team) {
            super(source, team);
        }
    }

    public static class DeleteEvent extends TeamFormEvent {
        DeleteEvent(TeamForm source, Team team) {
            super(source, team);
        }

    }

    public static class CloseEvent extends TeamFormEvent {
        CloseEvent(TeamForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}