package io.github.milobotdev.discordoauth2api.models;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record User(
        @NotNull String id,
        @NotNull String username,
        @NotNull String discriminator,
        @Nullable @SerializedName("global_name") String globalName,
        @NotNull String avatar,
        @Nullable Boolean bot,
        @Nullable Boolean system,
        @Nullable @SerializedName("mfa_enabled") Boolean mfaEnabled,
        @Nullable String banner,
        @Nullable@SerializedName("accent_color") Integer accentColor,
        @Nullable String locale,
        @Nullable Boolean verified,
        @Nullable String email,
        @NotNull Integer flags,
        @Nullable @SerializedName("premium_type") Integer premiumType,
        @Nullable @SerializedName("public_flags") Integer publicFlags
) {
}
