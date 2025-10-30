package com.example.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "management.server.port=9090", // actuator on 9090
                "management.endpoints.web.exposure.include=health,info,metrics",
                "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://dummy", // avoid real Keycloak
                "actuator.username=admin",
                "actuator.password=admin"
        }
)
class ActuatorSecurityTest {

    TestRestTemplate rest = new TestRestTemplate();

    String m(String path) { return "http://127.0.0.1:9090" + path; }

    @Test void health_isPublic() {
        assertThat(rest.getForEntity(m("/actuator/health"), String.class)
                .getStatusCode().value()).isEqualTo(200);
    }

    @Test void metrics_requiresAuth() {
        assertThat(rest.getForEntity(m("/actuator/metrics"), String.class)
                .getStatusCode().value()).isEqualTo(401);
    }

    @Test void metrics_allowsWithBasicAuth() {
        var authed = new TestRestTemplate("admin", "admin");
        assertThat(authed.getForEntity(m("/actuator/metrics"), String.class)
                .getStatusCode().value()).isEqualTo(200);
    }
}