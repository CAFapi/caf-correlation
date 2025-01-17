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
package com.github.cafapi.correlation.spring;

import static com.github.cafapi.correlation.constants.CorrelationIdConfigurationConstants.HEADER_NAME;
import static com.github.cafapi.correlation.constants.CorrelationIdConfigurationConstants.MDC_KEY;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public final class CorrelationIdInterceptor implements AsyncHandlerInterceptor
{
    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    )
    {
        final String correlationId = Optional.ofNullable(request.getHeader(HEADER_NAME))
            .filter(s -> !s.isEmpty())
            .orElseGet(() -> UUID.randomUUID().toString());
        MDC.put(MDC_KEY, correlationId);
        response.setHeader(HEADER_NAME, correlationId);
        return true;
    }

    @Override
    public void postHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler,
        final ModelAndView modelAndView
    )
    {
        MDC.remove(MDC_KEY);
    }
}
