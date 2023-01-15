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
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sanity checks that the server context will start
 */
@SpringBootTest
@ActiveProfiles("test")
class SecretSantaApiApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(SecretSantaApiApplicationTests.class);

	@MockBean
	private ApiSecurityConfig apiSecurityConfig;

	@MockBean
	private PersonRepository personRepository;

	@Autowired
	private PersonApiController personApiController;

	@Test
	void contextLoads() {
		LOG.info("Context loaded");
		assertThat(personApiController).isNotNull();
	}

}
