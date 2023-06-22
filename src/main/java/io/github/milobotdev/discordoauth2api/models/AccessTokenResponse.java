package io.github.milobotdev.discordoauth2api.models;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

public record AccessTokenResponse(
        @NotNull @SerializedName("access_token") String accessToken,
        @NotNull @SerializedName("refresh_token") String refreshToken,
        @SerializedName("expires_in") int expiresIn,
        @NotNull @SerializedName("scope") String scope,
        @NotNull @SerializedName("token_type") String tokenType
) {
}
