package fr.btn.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.btn.entities.MailEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    @JsonProperty(index = 1)
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    private Integer id;

    @JsonProperty(index = 2)
    private MailSenderDto sender;
    @JsonProperty(index = 3)
    private String recipient;
    @JsonProperty(index = 4)
    private String subject;

    @JsonProperty(index = 5)
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    private LocalTime time;

    @JsonProperty(index = 6)
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    private LocalDate date;

    @Getter
    public class MailSenderDto {
        Integer id;
        String name;
        String email;
    }
}
