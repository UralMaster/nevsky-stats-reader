package com.application.messages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Configuration for sending birthday related messages via Vkontakte API
 *
 * @author Ilya Ryabukhin
 * @since 06.06.2024
 */
@Configuration
public class VkApiConfiguration {

    @Value("${vk.group.id}")
    private Long groupId;

    @Value("${vk.group.access.token}")
    private String groupAccessToken;

    @Value("${vk.recipients}")
    private String recipients;

    private List<Long> recipientsList;

    @PostConstruct
    private void convertRecipients() {
        recipientsList = Arrays.stream(recipients.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    /**
     * @return id of Vkontakte's group on behalf of which message will be sent
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * @return access token of Vkontakte's group on behalf of which message will be sent
     */
    public String getGroupAccessToken() {
        return groupAccessToken;
    }

    /**
     * @return list of recipients (Vkontakte's users) to whom message should be sent
     */
    public String getRecipients() {
        return recipients;
    }

    /**
     * @return converted list of recipients (Vkontakte's users) to whom message should be sent in form or array
     */
    public List<Long> getRecipientsList() {
        return recipientsList;
    }

}
