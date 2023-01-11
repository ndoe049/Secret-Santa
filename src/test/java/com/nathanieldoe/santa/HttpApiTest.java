package com.nathanieldoe.santa;

import com.nathanieldoe.santa.security.ApiSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpApiTest {

    @Value("${local.server.port}")
    private int port;

    @MockBean
    private ApiSecurityConfig apiSecurityConfig;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void health() {
        assertThat(this.restTemplate
                .getForObject("http://localhost:" + port + "/actuator/health", String.class))
                .contains("status");
    }
}
