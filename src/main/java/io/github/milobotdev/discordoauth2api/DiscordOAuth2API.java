package io.github.milobotdev.discordoauth2api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.milobotdev.discordoauth2api.models.Guild;
import io.github.milobotdev.discordoauth2api.models.AccessTokenResponse;
import io.github.milobotdev.discordoauth2api.models.User;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class DiscordOAuth2API {

    @NotNull
    public static String getAuthorizationUrl(String clientId, String scope, String state, String redirectUri, String prompt) {
        String authorizationUrl;
        String clientIdUrlEncoded = URLEncoder.encode(clientId, StandardCharsets.UTF_8).replace("+", "%20");
        String redirectUriUrlEncoded = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8).replace("+", "%20");
        String scopesUrlEncoded = URLEncoder.encode(scope, StandardCharsets.UTF_8).replace("+", "%20");
        String stateUrlEncoded = URLEncoder.encode(state, StandardCharsets.UTF_8).replace("+", "%20");
        String promptUrlEncoded = URLEncoder.encode(prompt, StandardCharsets.UTF_8).replace("+", "%20");
        authorizationUrl = String.format("https://discord.com/oauth2/authorize?response_type=code&client_id=%s&scope=%s&state=%s&redirect_uri=%s&prompt=%s", clientIdUrlEncoded, scopesUrlEncoded, stateUrlEncoded, redirectUriUrlEncoded, promptUrlEncoded);
        return authorizationUrl;
    }

    @NotNull
    public static AccessTokenResponse exchangeAccessToken(String clientId, String clientSecret, String code, String redirectUri) throws IOException, InterruptedException, HttpException {
        String uri = "https://discord.com/api/v10/oauth2/token";
        String clientIdUrlEncoded;
        String clientSecretUrlEncoded;
        String codeUrlEncoded;
        String redirectUriUrlEncoded;
        clientIdUrlEncoded = URLEncoder.encode(clientId, StandardCharsets.UTF_8);
        clientSecretUrlEncoded = URLEncoder.encode(clientSecret, StandardCharsets.UTF_8);
        codeUrlEncoded = URLEncoder.encode(code, StandardCharsets.UTF_8);
        redirectUriUrlEncoded = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        String postData = String.format("client_id=%s&client_secret=%s&grant_type=authorization_code&code=%s&redirect_uri=%s", clientIdUrlEncoded, clientSecretUrlEncoded, codeUrlEncoded, redirectUriUrlEncoded);
        HttpRequest req = HttpRequest
                .newBuilder(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(postData))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new HttpException(response, "HTTP status code " + response.statusCode() + " didn't match expected 200.");
        } else {
            return new Gson().fromJson(response.body(), AccessTokenResponse.class);
        }
    }

    @NotNull
    public static AccessTokenResponse exchangeRefreshToken(String clientId, String clientSecret, String refreshToken) throws IOException, InterruptedException, HttpException {
        String uri = "https://discord.com/api/v10/oauth2/token";
        String clientIdUrlEncoded;
        String clientSecretUrlEncoded;
        String refreshTokenUrlEncoded;
        clientIdUrlEncoded = URLEncoder.encode(clientId, StandardCharsets.UTF_8);
        clientSecretUrlEncoded = URLEncoder.encode(clientSecret, StandardCharsets.UTF_8);
        refreshTokenUrlEncoded = URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);
        String postData = String.format("client_id=%s&client_secret=%s&grant_type=refresh_token&refresh_token=%s", clientIdUrlEncoded, clientSecretUrlEncoded, refreshTokenUrlEncoded);
        HttpRequest req = HttpRequest
                .newBuilder(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(postData))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new HttpException(response, "HTTP status code " + response.statusCode() + " didn't match expected 200.");
        } else {
            return new Gson().fromJson(response.body(), AccessTokenResponse.class);
        }
    }

    @NotNull
    public static User fetchUser(String accessToken) throws IOException, InterruptedException, HttpException {
        String uri = "https://discord.com/api/v10/users/@me";
        HttpRequest req = HttpRequest.newBuilder(URI.create(uri)).header("Authorization", "Bearer " + accessToken).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new HttpException(response, "HTTP status code " + response.statusCode() + " didn't match expected 200.");
        } else {
            return new GsonBuilder().serializeNulls().create().fromJson(response.body(), User.class);
        }
    }

    @NotNull
    public static Guild[] fetchGuilds(String accessToken) throws IOException, InterruptedException, HttpException {
        String uri = "https://discord.com/api/v10/users/@me/guilds";
        HttpRequest req = HttpRequest.newBuilder(URI.create(uri)).header("Authorization", "Bearer " + accessToken).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new HttpException(response, "HTTP status code " + response.statusCode() + " didn't match expected 200.");
        } else {
            return new GsonBuilder().serializeNulls().create().fromJson(response.body(), Guild[].class);
        }
    }
}
