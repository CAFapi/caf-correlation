# CAF Correlation JAXRS
This module encapsulates an implementation of `javax.ws.rs.client.ClientRequestFilter`. This client filter when registered with the client would set the correlation id in the outgoing request's header. The filter gets the correlation id from the MDC context(or creates one if it doesn't  find one).

## Usage
```
Client client = builder.build(...);
client.register(new CorrelationIdClientFilter(configuration));
``` 