package io.github.milobotdev.discordoauth2api.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Guild(
        @NotNull String id,
        @NotNull String name,
        @Nullable String icon,
        boolean owner,
        @NotNull String permissions,
        @NotNull String[] features
) {
}
