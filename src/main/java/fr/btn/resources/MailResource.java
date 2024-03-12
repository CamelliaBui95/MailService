package fr.btn.resources;

import fr.btn.dtos.ClientDto;
import fr.btn.dtos.MailClient;
import fr.btn.dtos.MailDto;
import fr.btn.dtos.MailSent;
import fr.btn.hateos.HateOs;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;

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
    public Response send(@HeaderParam("x-api-key") String apikey, MailSent mailSent) throws URISyntaxException {
        //Check if mail is not null
        if(mailSent == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        //Check if api key is valid
        ClientDto clientDto = apiKeyService.getClientByApiKey(apikey);



        if(clientDto == null) {
            HateOs hateOs = new HateOs();
            hateOs.addLink("Register", HttpMethod.POST, new URI("http://localhost:8082/clients"));

            return Response
                    .ok(hateOs.getLinks())
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }


        //Check if mail count < quota
        if(!isMailCountValid(apikey, clientDto)) {
            HateOs hateOs = new HateOs();
            hateOs.addLink("Augment Quota", HttpMethod.PUT, new URI("http://localhost:8082/clients/new_quota"));
            return Response
                    .status(Response.Status.NOT_ACCEPTABLE)
                    .build();

        }


        MailClient mailClient = MailClient
                .builder()
                .subject(mailSent.getSubject())
                .content(mailSent.getContent())
                .recipient(mailSent.getRecipient())
                .build();

        try{
            mailer.send(
                    Mail.withText(mailSent.getRecipient(), mailSent.getSubject(), mailSent.getContent())
            );

            mailClient.setDate(LocalDate.now());
            mailClient.setTime(LocalTime.now());

            MailDto mailDto = apiKeyService.saveMail(apikey, mailClient);

            // 201
            return Response.ok(mailDto).status(Response.Status.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();

            // 500
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean isMailCountValid(String apiKey, ClientDto client) {
        if(client.getMonthlyAllocation() == 0)
            return true;

        int mailCount = apiKeyService.getMailCount(apiKey);

        return mailCount < client.getMonthlyAllocation();
    }
}
