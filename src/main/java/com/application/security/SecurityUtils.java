package com.application.security;

import com.vaadin.flow.server.HandlerHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

/**
 * Util class for security related operations
 *
 * @author Ilya Ryabukhin
 * @since 23.03.2023
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Checks request for if this request is framework internal
     *
     * @param request request for check
     * @return true in case of this request is internal, false otherwise
     */
    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(RequestType.values())
                .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

}
