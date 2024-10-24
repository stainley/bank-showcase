package com.salapp.bank.configserver.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/*@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers*/
class ConfigServerIntegrationTests {

    /*@LocalServerPort
    private int port;

    @Value("${spring.application.name}")
    private String appName;

    public static GenericContainer<?> gitRepoContainer = new GenericContainer<>("gitscm/git")
            .withExposedPorts(80)
            .withCommand("git daemon --enable=receive-pack --base-path=/repo/ --verbose --detach")
            .withClasspathResourceMapping("config", "/repo", BindMode.READ_ONLY);


    @DynamicPropertySource
    static void registerWithGitRepoUrl(DynamicPropertyRegistry registry) {
        String gitRepoUrl = gitRepoContainer.getHost() + ":" + gitRepoContainer.getMappedPort(80) + "/config-repo";
        registry.add("spring.cloud.config.server.git.uri", () -> gitRepoUrl);
    }

    @Test
    void configServerServesConfiguration() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String response = restTemplate.getForObject("http://localhost:" + port + "/config", String.class);
        Assertions.assertThat(response).isEqualTo("property-from-git-repo");
    }

    @Test
    void applicationPropertiesLoaded() {
        Assertions.assertThat(appName).isEqualTo("config-server");
    }*/
}
