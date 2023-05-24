package com.application.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * Contains main info about {@link Tournament}'s season.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
@Entity
public class Season extends AbstractEntity {

    /**
     * Enumeration with possible divisions
     */
    public enum Division {
        PLCH_HIGHT("Высший дивизион ПЛЧ"),
        PLCH_FIRST("Первый дивизион ПЛЧ"),
        PLCH_SECOND_NORTH("Второй Северный дивизион ПЛЧ"),
        PLCH_SECOND_SOUTH("Второй Южный дивизион ПЛЧ"),
        PLCH_THIRD("Третий дивизион ПЛЧ"),
        PLCH_WINTER_HIGHT("Зимний Высший дивизион ПЛЧ"),
        PLCH_WINTER_FIRST("Зимний Первый дивизион ПЛЧ"),
        PLCH_WINTER_SECOND("Зимний Второй дивизион ПЛЧ"),
        PLCH_WINTER_THIRD("Зимний Третий дивизион ПЛЧ"),
        PLCH_WINTER_NORTH("Зимняя Лига Север ПЛЧ"),
        PLCH_WINTER_CUP("Зимний кубок ПЛЧ"),
        PLCH_CL("Лига чемпионов ПЛЧ"),
        PLCH_EL("Лига Европы (Кубок) ПЛЧ"),
        PLCH_INTERTOTO("Кубок Интертото ПЛЧ"),
        PLCH_SUPERCUP("Суперкубок ПЛЧ"),
        PLCH_QUALIFICATION("Кубковая квалификация ПЛЧ"),
        PLCH_ONE_DAY("Однодневный турнир ПЛЧ"),
        PLCH_RED_CUP("Ред Кап ПЛЧ"),
        PLCH_SUMMER_CUP_6x6("Летний Кубок 6х6 ПЛЧ"),
        NFL("Невская футбольная лига"),
        FFSPB_CUP_6x6("Кубок ФФСПб 6х6"),
        FRIENDLY_GAMES("Товарищеские игры"),
        PLAY_INN("Стыковые матчи"),
        OTHER("Другое");

        private final String divisionName;

        /**
         * Constructor
         *
         * @param divisionName textual name of division
         */
        Division(@NonNull String divisionName) {
            this.divisionName = divisionName;
        }

        @NonNull
        public String getDivisionName() {
            return divisionName;
        }
    }

    @Enumerated(EnumType.STRING)
    private Division division;

    @NotEmpty
    private String name = "";

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tournament")
    private Tournament tournament;

    /**
     * Constructor
     */
    public Season() {
    }

    @NonNull
    public Division getDivision() {
        return division;
    }

    public void setDivision(@NonNull Division division) {
        this.division = division;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull LocalDate startDate) {
        this.startDate = startDate;
    }

    @NonNull
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NonNull LocalDate endDate) {
        this.endDate = endDate;
    }

    @NonNull
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(@NonNull Tournament tournament) {
        this.tournament = tournament;
    }

}

