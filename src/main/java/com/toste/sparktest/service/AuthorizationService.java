package com.toste.sparktest.service;

import java.util.UUID;

public class AuthorizationService {
    // for this use case -- just using a hard coded random uuid
    private static final UUID AUTHORIZATION_TOKEN = UUID.fromString("6d99eab0-0f24-4585-a293-63f62c1a8e71");

    public boolean allowed(final String authToken) {
        try {
            final UUID token = UUID.fromString(authToken);
            return AUTHORIZATION_TOKEN.equals(token);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Authorization Token must be a valid UUID.");
        }
    }
}
