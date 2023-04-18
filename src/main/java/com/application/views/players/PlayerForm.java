package com.application.views.players;

import com.application.model.Player;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class PlayerForm extends FormLayout {
    TextField name = new TextField("Имя");
    DatePicker birthday = new DatePicker("День рождения");
    IntegerField games26Old = new IntegerField("Игр за Н26 (стар.)");
    IntegerField games54Old = new IntegerField("Игр за Н54 (стар.)");
    IntegerField gamesFriendlyOld = new IntegerField("Игр товар. (стар.)");
    IntegerField goals26Old = new IntegerField("Голов за Н26 (стар.)");
    IntegerField goals54Old = new IntegerField("Голов за Н54 (стар.)");
    IntegerField goalsFriendlyOld = new IntegerField("Голов товар. (стар.)");
    IntegerField assists26Old = new IntegerField("Передач за Н26 (стар.)");
    IntegerField assists54Old = new IntegerField("Передач за Н54 (стар.)");
    IntegerField assistsFriendlyOld = new IntegerField("Передач товар. (стар.)");
    IntegerField yellowCardsOld = new IntegerField("ЖК (стар.)");
    IntegerField redCardsOld = new IntegerField("КК (стар.)");
    ComboBox<Player.ActivityStatus> activityStatus = new ComboBox<>("Статус");

    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");

    Binder<Player> binder = new BeanValidationBinder<>(Player.class);

    private Player player;

    public PlayerForm(List<Player.ActivityStatus> statuses) {
        addClassName("player-form");
        binder.bindInstanceFields(this);

        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd-MM-yyyy");
        birthday.setI18n(singleFormatI18n);
        birthday.setPlaceholder("dd-MM-yyyy");

        games26Old.setMin(0);
        games54Old.setMin(0);
        gamesFriendlyOld.setMin(0);
        goals26Old.setMin(0);
        goals54Old.setMin(0);
        goalsFriendlyOld.setMin(0);
        assists26Old.setMin(0);
        assists54Old.setMin(0);
        assistsFriendlyOld.setMin(0);
        yellowCardsOld.setMin(0);
        redCardsOld.setMin(0);

        activityStatus.setItems(statuses);
        activityStatus.setItemLabelGenerator(Player.ActivityStatus::getTextualStatus);

        add(name,
                birthday,
                games26Old,
                games54Old,
                gamesFriendlyOld,
                goals26Old,
                goals54Old,
                goalsFriendlyOld,
                assists26Old,
                assists54Old,
                assistsFriendlyOld,
                yellowCardsOld,
                redCardsOld,
                activityStatus,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, player)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setPlayer(Player player) {
        this.player = player;
        binder.readBean(player);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(player);
            fireEvent(new SaveEvent(this, player));
        } catch (ValidationException e) {
            //TODO: logging
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class PlayerFormEvent extends ComponentEvent<PlayerForm> {
        private Player player;

        protected PlayerFormEvent(PlayerForm source, Player player) {
            super(source, false);
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }
    }

    public static class SaveEvent extends PlayerFormEvent {
        SaveEvent(PlayerForm source, Player player) {
            super(source, player);
        }
    }

    public static class DeleteEvent extends PlayerFormEvent {
        DeleteEvent(PlayerForm source, Player player) {
            super(source, player);
        }

    }

    public static class CloseEvent extends PlayerFormEvent {
        CloseEvent(PlayerForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
