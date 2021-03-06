/*
 * Copyright 2021 Micro Focus or one of its affiliates.
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
package com.github.cafapi.correlation.jservlet;

import com.github.cafapi.correlation.constants.CorrelationIdConfigurationConstants;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;

public class CorrelationIdFilter implements Filter
{
    @Override
    public void init(final FilterConfig filterConfig)
    {
    }

    @Override
    public void doFilter(
        final ServletRequest request,
        final ServletResponse response,
        final FilterChain chain
    ) throws IOException, ServletException
    {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final String correlationId = Optional.ofNullable(req.getHeader(CorrelationIdConfigurationConstants.HEADER_NAME))
            .filter(s -> !s.isEmpty())
            .orElseGet(() -> UUID.randomUUID().toString());
        MDC.put(CorrelationIdConfigurationConstants.MDC_KEY, correlationId);
        resp.addHeader(CorrelationIdConfigurationConstants.HEADER_NAME, correlationId);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {
    }
}
