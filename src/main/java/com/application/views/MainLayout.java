package com.application.views;

import com.application.views.games.GamesListView;
import com.application.views.players.PlayersListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(Lumo.class)
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
    }

    private void createHeader() {
        H2 name = new H2(" Невский 26/54. Командная статистика");
        name.addClassNames("text-l", "m-m");

        RouterLink playersListLink = new RouterLink("Игроки", PlayersListView.class);
        playersListLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink gamesListLink = new RouterLink("Игры", GamesListView.class);
        gamesListLink.setHighlightCondition(HighlightConditions.sameLocation());

        HorizontalLayout stubLayout = new HorizontalLayout();
        stubLayout.setWidth("100px");

        HorizontalLayout header = new HorizontalLayout(
                name,
                stubLayout,
                playersListLink,
                gamesListLink
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        header.setWidth("90%");
        header.setHeight("80px");
        header.expand(name);
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }
}