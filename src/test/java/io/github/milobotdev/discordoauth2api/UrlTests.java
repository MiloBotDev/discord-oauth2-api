/*
 * Copyright 2023 Gabor Szita
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
