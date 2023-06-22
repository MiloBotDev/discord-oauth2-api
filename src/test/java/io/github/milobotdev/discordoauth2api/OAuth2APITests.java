package io.github.milobotdev.discordoauth2api;

import com.google.gson.Gson;
import io.github.milobotdev.discordoauth2api.models.Guild;
import io.github.milobotdev.discordoauth2api.models.AccessTokenResponse;
import io.github.milobotdev.discordoauth2api.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


public class OAuth2APITests {

    public static final String fileName = "test-config.json";

    private static String clientId;
    private static String clientSecret;
    private static String redirectUri;

    private static boolean codeTestPassed = false;
    private static boolean exchangeAccessTokenTestPassed = false;
    private static boolean fetchUserTestPassed = false;
    private static boolean fetchGuildsTestPassed = false;
    private static boolean exchangeRefreshTokenTestPassed = false;
    private static boolean fetchUserAfterRefreshTokenExchangeTestPassed = false;

    @BeforeAll
    public static void init() throws IOException {
        assumeTrue(Files.exists(Paths.get(fileName)), "Test config file does not exist, skipping tests.");
        readConfig();
        doTests();
    }

    @Test
    public void testCode() {
        assertTrue(codeTestPassed);
    }

    @Test
    public void testExchangeAccessToken() {
        assertTrue(exchangeAccessTokenTestPassed);
    }

    @Test
    public void testFetchUser() {
        assertTrue(fetchUserTestPassed);
    }

    @Test
    public void testFetchGuilds() {
        assertTrue(fetchGuildsTestPassed);
    }

    @Test
    public void testExchangeRefreshToken() {
        assertTrue(exchangeRefreshTokenTestPassed);
    }

    @Test
    public void testFetchUserAfterRefreshTokenExchange() {
        assertTrue(fetchUserAfterRefreshTokenExchangeTestPassed);
    }

    private static void readConfig() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        Map<?, ?> map = gson.fromJson(reader, Map.class);
        clientId = (String) map.get("clientId");
        clientSecret = (String) map.get("clientSecret");
        redirectUri = (String) map.get("redirectUri");
    }

    private static void doTests() {
        Scanner userInput = new Scanner(System.in);
        String authorizationUrl = DiscordOAuth2API.getAuthorizationUrl(clientId, "identify guilds", "1234567890",
                redirectUri, "consent");
        System.out.println("Please go to the following URL in your browser, then hit return: " + authorizationUrl);
        userInput.nextLine();
        System.out.println("Please enter the code in the URL you were redirected to (the string next to 'code=') " +
                "or hit return if you don't have a valid code: ");
        String code = userInput.nextLine();
        if (code.equals("")) {
            System.out.println("No code entered, code test failed. No further tests will be run.");
            return;
        } else {
            System.out.println("Code entered, code test passed. Continuing...");
            codeTestPassed = true;
        }
        System.out.println("Testing exchange access token...");
        AccessTokenResponse accessTokenResponse;
        try {
            accessTokenResponse = DiscordOAuth2API.exchangeAccessToken(clientId, clientSecret, code, redirectUri);
        } catch (IOException | InterruptedException | HttpException e) {
            System.out.println("Exception thrown while exchanging auth token, exchange auth token test failed. " +
                    "No further tests will be run.");
            e.printStackTrace();
            return;
        }
        assertNotNull(accessTokenResponse);
        assertNotNull(accessTokenResponse.accessToken());
        assertNotNull(accessTokenResponse.refreshToken());
        assertNotEquals(0, accessTokenResponse.expiresIn());
        assertNotNull(accessTokenResponse.scope());
        exchangeAccessTokenTestPassed = true;
        System.out.println("Exchange access token test passed. Continuing...");
        System.out.println("Testing fetch user...");
        User user;
        try {
            user = DiscordOAuth2API.fetchUser(accessTokenResponse.accessToken());
        } catch (IOException | InterruptedException | HttpException e) {
            System.out.println("Exception thrown while getting user, get user test failed. " +
                    "No further tests will be run.");
            e.printStackTrace();
            return;
        }
        assertNotNull(user.id());
        assertNotNull(user.username());
        assertNotNull(user.discriminator());
        System.out.println("User information: User ID: " + user.id() + " Username: " + user.username() +
                " Discriminator: " + user.discriminator());
        fetchUserTestPassed = true;
        System.out.println("Fetch user test passed. Continuing...");
        System.out.println("Testing fetch guilds...");
        Guild[] guilds;
        try {
            guilds = DiscordOAuth2API.fetchGuilds(accessTokenResponse.accessToken());
        } catch (IOException | InterruptedException | HttpException e) {
            System.out.println("Exception thrown while getting guilds, get guilds test failed. " +
                    "No further tests will be run.");
            e.printStackTrace();
            return;
        }
        assertNotNull(guilds);
        if (guilds.length == 0) {
            System.out.println("WARNING: No guilds returned. This indicates that the user has not joined any guilds. " +
                    "This isn't an error, but joining at least one guild is required to fully test the API. " +
                    "Please join at least one guild to test the API.");
        }
        System.out.println("Guilds returned: " + guilds.length);
        for (Guild guild : guilds) {
            assertNotNull(guild.id());
            assertNotNull(guild.name());
            System.out.println("Guild information: Guild ID: " + guild.id() + " Guild Name: " + guild.name());
        }
        fetchGuildsTestPassed = true;
        System.out.println("Fetch guilds test passed. Continuing...");
        System.out.println("Testing exchange refresh token...");
        String originalAccessToken = accessTokenResponse.accessToken();
        try {
            accessTokenResponse = DiscordOAuth2API.exchangeRefreshToken(clientId, clientSecret,
                    accessTokenResponse.refreshToken());
        } catch (IOException | InterruptedException | HttpException e) {
            System.out.println("Exception thrown while exchanging refresh token, exchange refresh token test failed. " +
                    "No further tests will be run.");
            e.printStackTrace();
            return;
        }
        assertNotNull(accessTokenResponse);
        assertNotEquals(originalAccessToken, accessTokenResponse.accessToken());
        assertNotNull(accessTokenResponse.accessToken());
        assertNotNull(accessTokenResponse.refreshToken());
        assertNotEquals(0, accessTokenResponse.expiresIn());
        assertNotNull(accessTokenResponse.scope());
        exchangeRefreshTokenTestPassed = true;
        System.out.println("Exchange refresh token test passed. Continuing...");
        System.out.println("Testing user fetch after refresh token exchange to check if new access is still valid...");
        try {
            user = DiscordOAuth2API.fetchUser(accessTokenResponse.accessToken());
        } catch (IOException | InterruptedException | HttpException e) {
            System.out.println("Exception thrown while getting user, get user after refresh token exchange " +
                    "test failed. No further tests will be run.");
            e.printStackTrace();
            return;
        }
        assertNotNull(user.id());
        assertNotNull(user.username());
        assertNotNull(user.discriminator());
        System.out.println("User information: User ID: " + user.id() + " Username: " + user.username() +
                " Discriminator: " + user.discriminator());
        fetchUserAfterRefreshTokenExchangeTestPassed = true;
        System.out.println("Fetch user after refresh token exchange test passed. All tests passed.");
        System.out.println("ALL TESTS PASSED");
    }
}
