package fr.btn.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailClient {
    @JsonProperty(index = 1)
    private String recipient;
    @JsonProperty(index = 2)
    private String subject;
    @JsonProperty(index = 3)
    private String content;

}
