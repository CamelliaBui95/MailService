package fr.btn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import fr.btn.dtos.ClientDto;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;

import java.util.Collections;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockApiKeyService implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;
    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor(8089);

        ObjectMapper objectMapper = new ObjectMapper();

        ClientDto client1 = ClientDto
                .builder()
                .quota(2)
                .status("ACTIVE")
                .build();

        ClientDto client2 = ClientDto
                .builder()
                .quota(1)
                .status("ACTIVE")
                .build();

        String jsonClient1;
        String jsonClient2;

        try {
            jsonClient1 = objectMapper.writeValueAsString(client1);
            jsonClient2 = objectMapper.writeValueAsString(client2);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // mock apiKeyService.getClientByApiKey
        stubFor(
                get(urlEqualTo("/apiKey/TEST_KEY"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "application/json")
                                        .withStatus(200)
                                        .withBody(jsonClient1)
                        )
        );

        stubFor(
                get(urlEqualTo("/apiKey/TEST_KEY_2"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "application/json")
                                        .withStatus(200)
                                        .withBody(jsonClient2)
                        )
        );

        stubFor(
                get(urlEqualTo("/apiKey/FALSE_KEY"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "application/json")
                                        .withStatus(HttpStatus.SC_NOT_FOUND)
                        )
        );

        stubFor(
                post(urlEqualTo("/apiKey/TEST_KEY"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "application/json")
                                        .withStatus(200)
                        )
        );

        stubFor(
                get(urlEqualTo("/apiKey/FALSE_KEY/mail_count"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "text/plain")
                                        .withBody("0")
                        )
        );

        stubFor(
                get(urlEqualTo("/apiKey/TEST_KEY/mail_count"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "text/plain")
                                        .withBody("1")
                        )
        );

        stubFor(
                get(urlEqualTo("/apiKey/TEST_KEY_2/mail_count"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "text/plain")
                                        .withBody("1")
                        )
        );

        return Collections.singletonMap("%test.quarkus.rest-client.apiKey-service.url", wireMockServer.baseUrl());
    }

    @Override
    public void stop() {
        if(wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}
