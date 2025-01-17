/*
 * Copyright 2021-2025 Open Text.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cafapi.correlation.dropwizard.tests;

import static com.github.cafapi.correlation.constants.CorrelationIdConfigurationConstants.HEADER_NAME;
import com.github.cafapi.correlation.dropwizard.CorrelationIdBundle;
import io.dropwizard.core.Application;
import io.dropwizard.core.Configuration;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
final class CorrelationIdDropwizardTest
{
    private static final DropwizardAppExtension<Configuration> EXT = new DropwizardAppExtension<>(TestApp.class, new Configuration());

    @Test
    void testCorrelationIdIsAddedIfNotPresentInRequest()
    {
        final Client client = EXT.client();

        final Response response = client.target(
            String.format("http://localhost:%d/ping", EXT.getLocalPort()))
            .request()
            .get();

        Assertions.assertNotNull(response.getHeaders().get(HEADER_NAME));
    }

    @Test
    void testCorrelationIdIsAddedIfNullCorrelationInRequest()
    {
        final Client client = EXT.client();

        final Response response = client.target(
            String.format("http://localhost:%d/ping", EXT.getLocalPort()))
            .request()
            .header(HEADER_NAME, null)
            .get();

        Assertions.assertNotNull(response.getHeaders().get(HEADER_NAME));
        Assertions.assertNotNull(response.getHeaders().get(HEADER_NAME).get(0));
    }

    @Test
    void testCorrelationIdIsAddedIfEmptyCorrelationInRequest()
    {
        final Client client = EXT.client();

        final Response response = client.target(
            String.format("http://localhost:%d/ping", EXT.getLocalPort()))
            .request()
            .header(HEADER_NAME, "")
            .get();

        String correlationIdFromResponse = (String) response.getHeaders().get(HEADER_NAME).get(0);
        Assertions.assertNotNull(correlationIdFromResponse);
        Assertions.assertNotEquals("", correlationIdFromResponse);
    }

    @Test
    void testRequestWithHeader()
    {
        final Client client = EXT.client();

        final Response response = client.target(
            String.format("http://localhost:%d/ping", EXT.getLocalPort()))
            .request()
            .header(HEADER_NAME, "UUID1")
            .get();

        Assertions.assertEquals("UUID1", response.getHeaders().get(HEADER_NAME).get(0));
        Assertions.assertEquals(1, response.getHeaders().get(HEADER_NAME).size());
    }

    public static final class TestApp extends Application<Configuration>
    {
        @Override
        public void initialize(final Bootstrap<Configuration> bootstrap)
        {
            bootstrap.addBundle(new CorrelationIdBundle<>());
        }

        @Override
        public void run(final Configuration configuration, final Environment environment)
        {
            environment.jersey().register(this);
            environment.jersey().register(new PingResource());
        }
    }

    @Path("/ping")
    public static final class PingResource
    {
        @GET
        public String ping()
        {
            return "pong";
        }
    }
}
