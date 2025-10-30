package com.example.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "management.server.port=0",
                "management.endpoints.web.exposure.include=health,info,metrics",
                "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://dummy",
                "actuator.username=admin",
                "actuator.password=admin"
        }
)
class ActuatorSecurityTest {

    @Autowired
    private Environment env;

    private final TestRestTemplate rest = new TestRestTemplate();

    private String actuatorUrl(String path) {
        // Spring Boot automatically exposes this when management runs separately
        Integer mgmtPort = env.getProperty("local.management.port", Integer.class);
        if (mgmtPort == null) {
            mgmtPort = env.getProperty("local.server.port", Integer.class);
        }
        return "http://127.0.0.1:" + mgmtPort + path;
    }

    @Test
    void health_isPublic() {
        var resp = rest.getForEntity(actuatorUrl("/actuator/health"), String.class);
        assertThat(resp.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void metrics_requiresAuth() {
        var resp = rest.getForEntity(actuatorUrl("/actuator/metrics"), String.class);
        assertThat(resp.getStatusCode().value()).isEqualTo(401);
    }

    @Test
    void metrics_allowsWithBasicAuth() {
        var authed = new TestRestTemplate("admin", "admin");
        var resp = authed.getForEntity(actuatorUrl("/actuator/metrics"), String.class);
        assertThat(resp.getStatusCode().value()).isEqualTo(200);
    }
}