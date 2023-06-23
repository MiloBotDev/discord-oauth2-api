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

import java.net.http.HttpResponse;

public class HttpException extends Exception {

    private final HttpResponse<String> response;
    private final String message;

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
