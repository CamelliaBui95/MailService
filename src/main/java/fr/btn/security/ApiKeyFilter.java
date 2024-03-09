package fr.btn.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class ApiKeyFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String apiKey = requestContext.getHeaderString("X-API-Key");

        if (apiKey == null || apiKey.isEmpty()) {
            try {
                requestContext
                        .abortWith(Response
                                .status(Response.Status.UNAUTHORIZED)
                                .contentLocation(new URI("http://localhost:8085/api/register"))
                                .build());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
