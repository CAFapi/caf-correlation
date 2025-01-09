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
package com.github.cafapi.correlation.dropwizard;

import com.github.cafapi.correlation.jservlet.CorrelationIdFilter;
import io.dropwizard.core.Configuration;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import java.util.EnumSet;

public class CorrelationIdBundle<C extends Configuration> implements ConfiguredBundle<C>
{
    @Override
    public void initialize(final Bootstrap<?> bootstrap)
    {
    }

    @Override
    public void run(final C configuration, final Environment environment)
    {
        environment.servlets()
            .addFilter("correlation-id-servlet-filter", new CorrelationIdFilter())
            .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "*");
    }
}
