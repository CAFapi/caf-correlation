# CAF Correlation Dropwizard

This module includes a dropwizard bundle which encapsulates a `javax.servlet.Filter` implementation for managing the correlation id in dropwizard applications.

## How it works
The filter gets correlation id from incoming request(or creates one if it doesn't exist) and sets it into the MDC context. In addition to that it also adds the correlation id to the outgoing response header. 

## Usage 
Add bundle to your dropwizard configuration

```
public void initialize(Bootstrap<MyApplicationConfiguration> bootstrap) {
    bootstrap.addBundle(new CorrelationIdBundle<>()));
}
```