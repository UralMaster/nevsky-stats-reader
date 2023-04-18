package com.application.views.tournaments;

import com.application.model.Tournament;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class TournamentForm extends FormLayout {
    TextField name = new TextField("Название");

    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");

    Binder<Tournament> binder = new BeanValidationBinder<>(Tournament.class);

    private Tournament tournament;

    public TournamentForm() {
        addClassName("tournament-form");
        binder.bindInstanceFields(this);

        add(name, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, tournament)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        binder.readBean(tournament);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(tournament);
            fireEvent(new SaveEvent(this, tournament));
        } catch (ValidationException e) {
            //TODO: logging
            e.printStackTrace();
        }
    }

    //Events
    public static abstract class TournamentFormEvent extends ComponentEvent<TournamentForm> {
        private Tournament tournament;

        protected TournamentFormEvent(TournamentForm source, Tournament tournament) {
            super(source, false);
            this.tournament = tournament;
        }

        public Tournament getTournament() {
            return tournament;
        }
    }

    public static class SaveEvent extends TournamentFormEvent {
        SaveEvent(TournamentForm source, Tournament tournament) {
            super(source, tournament);
        }
    }

    public static class DeleteEvent extends TournamentFormEvent {
        DeleteEvent(TournamentForm source, Tournament tournament) {
            super(source, tournament);
        }
    }

    public static class CloseEvent extends TournamentFormEvent {
        CloseEvent(TournamentForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
