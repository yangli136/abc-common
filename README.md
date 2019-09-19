_README FILE_

---

# Common Utilities for Spring Boot Based Applications.

This is a Spring Boot based application with some of the most common used Spring framework, Spring Boot features
integrated in:
1. Async task execution
1. Cache
1. Job Scheduling
1. Metrics based on Micrometer
1. Retry


The library provides utilities for the following common cross-cutting concerns:

1. Debugging support for logging method name, method input parameters, method return value, exceptions thrown.
   The implementation is based on Spring AOP and will be applied to all service and impl packages automatically. The
   feature could be disabled with an @MethodDebuggingDisable annotation if a method is sensitive to extra overhead
   introduced by AOP.

1. Statistic gathering support: the success, failure, counter, method execution elapsed time will be collected for
   methods with @Timed annotation by the TimedAndCountedAspect apsect implementation. The statistics gathering mechanism
   is Micrometer.

1. Retry support based on Spring Retry. Multiple retry strategies are supported: simple, exponential.

1. Log4j2 MDC(Mapped Diagnostic Context) support. UUID, hostname, appname information could be added into logs with
   @Log4jDiagonosticContextEnable annotation. 

1. Spring DevTool, Spring Actuator, Spring Boot Maven Plugin are configured and provide development and production
   monitoring features.

### Run locally:

    mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dweb.test.enabled=true"

with log4j2 

    mvn -o spring-boot:run -Dspring-boot.run.jvmArguments="-Dlogging.config=classpath:log4j2.properties -Dweb.test.enabled=true"

