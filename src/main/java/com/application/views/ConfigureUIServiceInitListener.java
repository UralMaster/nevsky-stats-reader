package com.application.views;

import com.application.views.games.GamesListView;
import com.application.views.players.PlayersListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

/**
 * {@link VaadinServiceInitListener} implementation which provides ability of rerouting to {@link PlayersListView} page
 * from any other pages which are not {@link PlayersListView} or {@link GamesListView}
 *
 * @author Ilya Ryabukhin
 * @since 19.04.2023
 */
@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::toPlayersListViewNavigation);
        });
    }

    private void toPlayersListViewNavigation(BeforeEnterEvent event) {
        if (!PlayersListView.class.equals(event.getNavigationTarget()) &&
                !GamesListView.class.equals(event.getNavigationTarget())) {
            event.rerouteTo(PlayersListView.class);
        }
    }
}
