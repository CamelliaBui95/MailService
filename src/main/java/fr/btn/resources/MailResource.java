package fr.btn.resources;

import fr.btn.dtos.ClientDto;
import fr.btn.dtos.MailClient;
import fr.btn.services.ApiKeyService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@Path("/mail")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name="Mail Service")
public class MailResource {
    @Inject
    Mailer mailer;

    @Inject
    @RestClient
    ApiKeyService apiKeyService;

    @POST
    @Blocking
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response send(@HeaderParam("x-api-key") String apikey, MailClient mailClient) {
        //Check if mail is not null
        if(apikey == null || mailClient == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        //Check if api key is valid
        ClientDto clientDto = apiKeyService.getClientByApiKey(apikey);

        if(clientDto == null)
            return Response
                    .ok("Client Not Found.")
                    .status(Response.Status.NOT_FOUND)
                    .build();

        //Check if mail count < quota
        if(!isMailCountValid(apikey, clientDto))
            return Response
                    .ok("Quota has been reached.")
                    .status(Response.Status.NOT_ACCEPTABLE)
                    .build();

        try{
            mailer.send(
                    Mail.withText(mailClient.getRecipient(), mailClient.getSubject(), mailClient.getContent())
            );

            return apiKeyService.saveMail(apikey, mailClient);
        } catch (Exception e) {
            e.printStackTrace();
            // 500
            return Response.ok("Cannot send mail.").status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
// validator de mail
// messages personalisés
    private boolean isMailCountValid(String apiKey, ClientDto client) {
        if(client.getQuota() == 0)
            return true;

        int mailCount = apiKeyService.getMailCountByMonth(apiKey);

        return mailCount < client.getQuota();
    }
}
