package com.application.views.seasons;

import com.application.model.Season;
import com.application.model.Tournament;
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

public class SeasonForm extends FormLayout {
    ComboBox<Tournament> tournament = new ComboBox<>("Турнир");
    ComboBox<Season.Division> division = new ComboBox<>("Дивизион");
    TextField name = new TextField("Наименование");
    DatePicker startDate = new DatePicker("Дата начала");
    DatePicker endDate = new DatePicker("Дата окончания");

    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");

    Binder<Season> binder = new BeanValidationBinder<>(Season.class);

    private Season season;

    public SeasonForm(List<Season.Division> divisions, List<Tournament> tournaments) {
        addClassName("season-form");
        binder.bindInstanceFields(this);

        tournament.setItems(tournaments);
        tournament.setItemLabelGenerator(Tournament::getName);

        division.setItems(divisions);
        division.setItemLabelGenerator(Season.Division::getDivisionName);

        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd-MM-yyyy");
        startDate.setI18n(singleFormatI18n);
        startDate.setPlaceholder("dd-MM-yyyy");
        endDate.setI18n(singleFormatI18n);
        endDate.setPlaceholder("dd-MM-yyyy");

        add(tournament,
            division,
            name,
            startDate,
            endDate,
            createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, season)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setSeason(Season season) {
        this.season = season;
        binder.readBean(season);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(season);
            fireEvent(new SaveEvent(this, season));
        } catch (ValidationException e) {
            //TODO: logging
            e.printStackTrace();
        }
    }

    //Events
    public static abstract class SeasonFormEvent extends ComponentEvent<SeasonForm> {
        private Season season;

        protected SeasonFormEvent(SeasonForm source, Season season) {
            super(source, false);
            this.season = season;
        }

        public Season getSeason() {
            return season;
        }
    }

    public static class SaveEvent extends SeasonFormEvent {
        SaveEvent(SeasonForm source, Season season) {
            super(source, season);
        }
    }

    public static class DeleteEvent extends SeasonFormEvent {
        DeleteEvent(SeasonForm source, Season season) {
            super(source, season);
        }
    }

    public static class CloseEvent extends SeasonFormEvent {
        CloseEvent(SeasonForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
