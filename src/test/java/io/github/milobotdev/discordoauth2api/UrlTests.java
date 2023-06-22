package io.github.milobotdev.discordoauth2api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlTests {

    @Test
    public void testAuthorizationUrl() {
        String clientId = "157730590492196864";
        String scope = "identify guilds.join";
        String state = "15773059ghq9183habn";
        String redirectUri = "https://nicememe.website";
        String prompt = "consent";
        String actualAuthorizationUrl = DiscordOAuth2API.getAuthorizationUrl(clientId, scope, state, redirectUri, prompt);
        assertEquals("https://discord.com/oauth2/authorize?response_type=code&client_id=157730590492196864&scope=identify%20guilds.join&state=15773059ghq9183habn&redirect_uri=https%3A%2F%2Fnicememe.website&prompt=consent", actualAuthorizationUrl);
    }
}
