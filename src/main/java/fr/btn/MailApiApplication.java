package fr.btn;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info =
@Info(
        title = "Mail Api",
        version = "1.0",
        description = "Mail Service Api",
        contact = @Contact(url = "http://www.afpa.fr/", name = "MailApi", email = "contact@afpa.fr")
),
        servers = @Server(
                url = "http://localhost:8080/"
        )
)
/*@SecurityScheme(
        securitySchemeName = "x-api-key",
        apiKeyName = "x-api-key",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        scheme = "ApiKeyScheme"
)*/
@ApplicationPath("api")
public class MailApiApplication extends Application {
}
