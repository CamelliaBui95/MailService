package fr.btn.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailSent {
    private String recipient;
    private String subject;
    private String content;
}
