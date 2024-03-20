package fr.btn;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

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

        stubFor(
                post(urlEqualTo("/apiKey/TEwLHA9MSRYWG5EO"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "application/json")
                                        .withStatus(200)
                        )
        );

        stubFor(
                get(urlMatching(".*"))
                        .atPriority(10)
                        .willReturn(aResponse().proxiedFrom("http://localhost:8081"))
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
