package fr.btn.services;

import fr.btn.dtos.ClientDto;
import fr.btn.dtos.MailClient;
import fr.btn.dtos.MailDto;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "apiKey-service")
@Path("/apiKey")
public interface ApiKeyService {
    @GET
    @Path("{apiKey}")
    ClientDto getClientByApiKey(@PathParam("apiKey") String apiKey);

    @GET
    @Path("/mailCount/{apiKey}")
    int getMailCount(@PathParam("apiKey") String apiKey);

    @POST
    @Transactional
    @Path("{apiKey}")
    MailDto saveMail(@PathParam("apiKey") String apiKey, MailClient mailClient);
}
