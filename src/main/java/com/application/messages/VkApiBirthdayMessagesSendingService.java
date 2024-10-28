package com.application.messages;

import com.application.model.Player;
import com.application.service.PlayerService;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.messages.MessagesSendQueryWithUserIds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Sender of birthday related messages via Vkontakte REST API through provided Java SDK
 *
 * @author Ilya Ryabukhin
 * @since 06.06.2024
 */
@Service
public class VkApiBirthdayMessagesSendingService {
    private static final Logger logger = LoggerFactory.getLogger(VkApiBirthdayMessagesSendingService.class);

    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    private final VkApiClient vk;
    private final VkApiConfiguration vkApiConfiguration;
    private final GroupActor groupActor;
    private final PlayerService playerService;

    /**
     * Constructor
     *
     * @param vkApiConfiguration configuration instance with necessary API related properties
     * @param playerService      service for {@link Player}
     */
    public VkApiBirthdayMessagesSendingService(@NonNull VkApiConfiguration vkApiConfiguration,
                                               @NonNull PlayerService playerService) {
        TransportClient transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);
        this.vkApiConfiguration = vkApiConfiguration;
        this.groupActor = new GroupActor(
                vkApiConfiguration.getGroupId(),
                vkApiConfiguration.getGroupAccessToken()
        );
        this.playerService = playerService;
    }

    @PostConstruct
    private void scheduleExecutorService() {
        service.scheduleAtFixedRate(
                this::sendMessageToRecipients,
                2,
                86400,
                TimeUnit.SECONDS
        );
    }

    private void sendMessageToRecipients() {
        String message = prepareMessage();
        if (message == null) {
            return;
        }

        List<Long> recipients = vkApiConfiguration.getRecipientsList();
        for (Long recipient : recipients) {
            sendMessage(recipient, message);
        }
    }

    @Nullable
    private String prepareMessage() {
        LocalDate currentDate = LocalDate.now();
        LocalDate tomorrowDate = currentDate.plusDays(1);

        List<Player> activePlayersWithBirthdays = playerService.findAllActivePlayers().stream()
                .filter(player -> player.getBirthday() != null)
                .collect(Collectors.toUnmodifiableList());

        if (activePlayersWithBirthdays.isEmpty()) {
            return null;
        }

        List<Player> today = activePlayersWithBirthdays.stream()
                .filter(player -> player.getBirthday().getMonth() == currentDate.getMonth()
                        && player.getBirthday().getDayOfMonth() == currentDate.getDayOfMonth())
                .collect(Collectors.toUnmodifiableList());

        List<Player> tomorrow = activePlayersWithBirthdays.stream()
                .filter(player -> player.getBirthday().getMonth() == tomorrowDate.getMonth()
                        && player.getBirthday().getDayOfMonth() == tomorrowDate.getDayOfMonth())
                .collect(Collectors.toUnmodifiableList());

        StringBuilder messageBuilder = new StringBuilder("Ближайшие дни рождения:");
        messageBuilder.append("\n")
                .append("\n");

        boolean isEmptyResult = true;

        if (!today.isEmpty()) {
            isEmptyResult = false;
            messageBuilder.append("СЕГОДНЯ - ")
                    .append(currentDate)
                    .append(":")
                    .append("\n");

            for (Player player : today) {
                messageBuilder.append(player.getName())
                        .append("\n");
            }

            messageBuilder.append("\n");

            logger.debug("Today's = {} birthday related message will contain info regarding the = {}",
                    currentDate, today);
        }

        if (!tomorrow.isEmpty()) {
            isEmptyResult = false;
            messageBuilder.append("ЗАВТРА - ")
                    .append(tomorrowDate)
                    .append(":")
                    .append("\n");

            for (Player player : tomorrow) {
                messageBuilder.append(player.getName())
                        .append("\n");
            }

            messageBuilder.append("\n");

            logger.debug("Tomorrow's = {} birthday related message will contain info regarding the = {}",
                    tomorrowDate, tomorrow);
        }

        logger.debug("Today's = {} birthday related message was prepared", currentDate);

        return isEmptyResult ? null : messageBuilder.toString();
    }

    private void sendMessage(@NonNull Long recipient, @NonNull String message) {
        MessagesSendQueryWithUserIds query = vk.messages().sendUserIds(groupActor)
                .message(message)
                .randomId(0)
                .userId(recipient);
        try {
            query.executeAsRaw();
            logger.debug("Message = {} was successfully sent to = {}", message, recipient);
        } catch (Exception e) {
            logger.error("Exception occurred during sending of message = {} for recipient = {}", message, recipient);
            logger.error("Error from VK API: ", e);
        }
    }

}
