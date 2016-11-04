Resources in this directory are to be accessed via the URL /versioned-resources-X/** where X is supplied by the resources.version property in configuration.properties
When resources.version changes, clients should access the new version. This prevents them from holding onto cached copies.
For setup, see the WebConfig class.
Reference:
http://docs.spring.io/spring/docs/3.2.7.RELEASE/spring-framework-reference/htmlsingle/#mvc-config-static-resources