package com.nathanieldoe.santa;

import com.nathanieldoe.santa.controller.PersonApiController;
import com.nathanieldoe.santa.db.PersonRepository;
import com.nathanieldoe.santa.security.ApiSecurityConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sanity checks that the server context will start
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecretSantaApiApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(SecretSantaApiApplicationTests.class);

	@MockBean
	private ApiSecurityConfig apiSecurityConfig;

	@MockBean
	private PersonRepository personRepository;

	@Autowired
	private PersonApiController personApiController;

	@Container
	public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
			.withPassword("inmemory")
			.withUsername("inmemory");

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
	}

	@Test
	void contextLoads() {
		LOG.info("Context loaded");
		assertThat(personApiController).isNotNull();
	}

}
