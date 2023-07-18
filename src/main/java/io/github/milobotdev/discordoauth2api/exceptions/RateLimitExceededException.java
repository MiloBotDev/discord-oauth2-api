package io.github.milobotdev.discordoauth2api.exceptions;

import io.github.milobotdev.discordoauth2api.models.RateLimitExceededResponse;

import java.net.http.HttpResponse;

public class RateLimitExceededException extends HttpException {

    private final RateLimitExceededResponse rateLimitExceededResponse;

    public RateLimitExceededException(HttpResponse<String> response, String message, RateLimitExceededResponse rateLimitExceededResponse) {
        super(response, message);
        this.rateLimitExceededResponse = rateLimitExceededResponse;
    }

    public RateLimitExceededResponse getRateLimitExceededResponse() {
        return rateLimitExceededResponse;
    }
}
