# CAF Correlation JServlet
This module encapsulates an implementation of `javax.servlet.Filter` which manages the Correlation id for the which uses this filter.

## How it works
The filter gets correlation id from incoming request(or creates one if it doesn't exist) and sets it into the MDC context. In addition to that it also adds the correlation id to the outgoing response header.

## Usage
This filter can be added in web.xml as mentioned below

```
  <filter>
    <filter-name>CorrelationIdFilter</filter-name>
    <filter-class>com.github.cafapi.correlation.jservlet.CorrelationIdFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>CorrelationIdFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```