package io.github.milobotdev.discordoauth2api.models;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record RateLimitExceededResponse(
        @NotNull String message,
        @SerializedName("retry_after") double retryAfter,
        boolean global,
        @Nullable Integer code
) {
}
