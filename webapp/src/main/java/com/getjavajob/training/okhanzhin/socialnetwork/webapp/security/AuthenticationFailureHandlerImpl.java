package com.getjavajob.training.okhanzhin.socialnetwork.webapp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);
    private static final String EMAIL = "email";
    private static final String ERROR = "logEmailPasError";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {

        logger.warn(e.getMessage(), e);

        if (findCause(e) instanceof LockedException) {
            request.setAttribute(ERROR, "Unfortunately, your account is locked, " +
                    "contact the administration of the social network.");
        } else {
            request.setAttribute(ERROR, "Failed to log in. Invalid Log/Password.");
        }
        request.setAttribute(EMAIL, request.getParameter(EMAIL));
        request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
    }

    private Throwable findCause(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }

        return rootCause;
    }
}
