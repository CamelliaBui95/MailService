package fr.btn.services;

import fr.btn.dtos.ClientDto;
import fr.btn.dtos.MailClient;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "apiKey-service")
@Path("/apiKey")
public interface ApiKeyService {
    @GET
    @Path("{apiKey}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getClientByApiKey(@PathParam("apiKey") String apiKey);

    @GET
    @Path("/{apiKey}/mail_count")
    @Produces(MediaType.TEXT_PLAIN)
    Response getMailCountByMonth(@PathParam("apiKey") String apiKey);

    @POST
    @Transactional
    @Path("{apiKey}")
    Response saveMail(@PathParam("apiKey") String apiKey, MailClient mailClient);
}

//(configKey = "apiKey-service")
