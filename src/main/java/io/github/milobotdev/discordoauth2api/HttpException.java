package io.github.milobotdev.discordoauth2api;

import java.net.http.HttpResponse;

public class HttpException extends Exception {

    HttpResponse<String> response;
    String message;

    public HttpException(HttpResponse<String> response, String message) {
        this.response = response;
        this.message = message;
    }

    public HttpResponse<String> getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }
}
