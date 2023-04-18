package com.application.views;

import com.application.security.SecurityService;
import com.application.views.games.GamesListView;
import com.application.views.players.PlayersListView;
import com.application.views.seasons.SeasonsListView;
import com.application.views.teams.TeamsListView;
import com.application.views.tournaments.TournamentsListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(Lumo.class)
public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
    }

    private void createHeader() {
        H2 name = new H2(" Невский 26/54. Командная статистика");
        name.addClassNames("text-l", "m-m");

        Button logout = new Button("Выход", e -> securityService.logout());

        RouterLink playersListLink = new RouterLink("Игроки", PlayersListView.class);
        playersListLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink gamesListLink = new RouterLink("Игры", GamesListView.class);
        gamesListLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink tournamentsListLink = new RouterLink("Турниры", TournamentsListView.class);
        tournamentsListLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink seasonsListLink = new RouterLink("Сезоны", SeasonsListView.class);
        seasonsListLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink teamsListLink = new RouterLink("Команды", TeamsListView.class);
        teamsListLink.setHighlightCondition(HighlightConditions.sameLocation());

        HorizontalLayout stubLayout = new HorizontalLayout();
        stubLayout.setWidth("100px");

        HorizontalLayout header = new HorizontalLayout(
                name,
                stubLayout,
                playersListLink,
                gamesListLink,
                tournamentsListLink,
                seasonsListLink,
                teamsListLink,
                logout
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        header.setWidth("100%");
        header.setHeight("80px");
        header.expand(name);
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }
}