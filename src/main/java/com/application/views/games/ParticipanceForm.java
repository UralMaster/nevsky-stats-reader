package com.application.views.games;

import com.application.model.Participance;
import com.application.model.Player;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ParticipanceForm extends FormLayout {
    ComboBox<Player> player = new ComboBox<>("Игрок");
    IntegerField goals = new IntegerField("Голов");
    IntegerField assists = new IntegerField("Передач");
    IntegerField yellowCards = new IntegerField("ЖК");
    IntegerField redCards = new IntegerField("КК");

    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");

    Binder<Participance> binder = new BeanValidationBinder<>(Participance.class);

    private Participance participance;

    public ParticipanceForm(List<Player> players) {
        addClassName("participance-form");
        binder.bindInstanceFields(this);

        player.setItems(players);
        player.setItemLabelGenerator(Player::getName);

        goals.setMin(0);
        assists.setMin(0);
        yellowCards.setMin(0);
        yellowCards.setMax(2);
        redCards.setMin(0);
        redCards.setMax(1);

        add(player,
                goals,
                assists,
                yellowCards,
                redCards,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, participance)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setParticipance(Participance participance) {
        this.participance = participance;
        binder.readBean(participance);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(participance);
            fireEvent(new SaveEvent(this, participance));
        } catch (ValidationException e) {
            //TODO: logging
            e.printStackTrace();
        }
    }

    //Events
    public static abstract class ParticipanceFormEvent extends ComponentEvent<ParticipanceForm> {
        private final Participance participance;

        protected ParticipanceFormEvent(ParticipanceForm source, Participance participance) {
            super(source, false);
            this.participance = participance;
        }

        public Participance getParticipance() {
            return participance;
        }
    }

    public static class SaveEvent extends ParticipanceFormEvent {
        SaveEvent(ParticipanceForm source, Participance participance) {
            super(source, participance);
        }
    }

    public static class DeleteEvent extends ParticipanceFormEvent {
        DeleteEvent(ParticipanceForm source, Participance participance) {
            super(source, participance);
        }
    }

    public static class CloseEvent extends ParticipanceFormEvent {
        CloseEvent(ParticipanceForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
