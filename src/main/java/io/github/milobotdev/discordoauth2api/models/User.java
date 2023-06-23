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
