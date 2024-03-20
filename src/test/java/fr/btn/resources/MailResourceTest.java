package fr.btn.resources;

import fr.btn.dtos.MailSent;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class MailResourceTest {
    private static final String TO = "foo@quarkus.io";
    private static final String API_KEY = "TEwLHA9MSRYWG5EO";

    @Inject
    MockMailbox mailbox;

    @BeforeEach
    void init() {
        mailbox.clear();
    }

    @Test
    void testTextMailWithValidApiKey() {
        MailSent testMail = MailSent
                .builder()
                .recipient(TO)
                .subject("TEST SUBJECT")
                .content("TEST CONTENT")
                .build();

        given()
                .contentType("application/json")
                .header("x-api-key", API_KEY)
                .body(testMail)
                .when()
                .post("/mail")
                .then()
                .statusCode(200);

        List<Mail> sent = mailbox.getMailsSentTo(TO);
        assertThat(sent, hasSize(1));

        Mail actual = sent.get(0);

        assertThat(actual.getSubject(), equalTo("TEST SUBJECT"));
        assertThat(actual.getText(), equalTo("TEST CONTENT"));
    }

    @Test
    void testTextMailWithInvalidApiKey() {
        MailSent testMail = MailSent
                .builder()
                .recipient(TO)
                .subject("TEST SUBJECT")
                .content("TEST CONTENT")
                .build();

        given()
                .contentType("application/json")
                .header("x-api-key", "zzz")
                .body(testMail)
                .when()
                .post("/mail")
                .then()
                .statusCode(404);
    }

    @Test
    void testTextMailWithNoApiKey() {
        MailSent testMail = MailSent
                .builder()
                .recipient(TO)
                .subject("TEST SUBJECT")
                .content("TEST CONTENT")
                .build();

        given()
                .contentType("application/json")
                .body(testMail)
                .when()
                .post("/mail")
                .then()
                .statusCode(400);
    }

}
