/*
 * Copyright 2017-2021 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.reactor.http.client.proxy;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.http.client.ProxyHttpClient;
import reactor.core.publisher.Flux;

import java.net.URL;

/**
 * Extended version of {@link ProxyHttpClient} for Project Reactor.
 *
 * @author James Kleeh
 * @since 2.1.0
 */
public interface ReactorProxyHttpClient extends ProxyHttpClient {

    @Override
    Flux<MutableHttpResponse<?>> proxy(@NonNull HttpRequest<?> request);

    /**
     * Create a new {@link ProxyHttpClient}.
     * Note that this method should only be used outside of the context of a Micronaut application.
     * The returned {@link ProxyHttpClient} is not subject to dependency injection.
     * The creator is responsible for closing the client to avoid leaking connections.
     * Within a Micronaut application use {@link jakarta.inject.Inject} to inject a client instead.
     *
     * @param url The base URL
     * @return The client
     */
    @NonNull
    static ReactorProxyHttpClient create(@Nullable URL url) {
        return new BridgedReactorProxyHttpClient(ProxyHttpClient.create(url));
    }

    /**
     * Create a new {@link ProxyHttpClient} with the specified configuration. Note that this method should only be used
     * outside of the context of an application. Within Micronaut use {@link jakarta.inject.Inject} to inject a client instead
     *
     * @param url The base URL
     * @param configuration the client configuration
     * @return The client
     * @since 2.2.0
     */
    @NonNull
    static ReactorProxyHttpClient create(@Nullable URL url, @NonNull HttpClientConfiguration configuration) {
        return new BridgedReactorProxyHttpClient(ProxyHttpClient.create(url, configuration));
    }
}
