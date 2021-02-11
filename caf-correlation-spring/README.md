# CAF Correlation Spring
This module contains spring framework's interceptor implementation for managing correlation id. This library can be used for Spring boot applications where it is required for the  correlation id to be available in MDC context for logging.

## How it works
This interceptor reads the correlation id from incoming request's header. If present, it is then added to the MDC context as well as to the outgoing response's header.

## Usage

```
public class SpringApplication implements WebMvcConfigurer {
    
    // Excluding rest of the code for brevity 

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new CorrelationIdInterceptor());
    }
}
```