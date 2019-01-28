package io.anymobi.services.jpa.security;

public interface LoginAttemptService {

    void loginSucceeded(final String key);
    void loginFailed(final String key);
    boolean isBlocked(final String key);
}
