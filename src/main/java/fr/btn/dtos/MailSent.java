package fr.btn.dtos;

import lombok.Data;

@Data
public class MailSent {
    private String recipient;
    private String subject;
    private String content;
}
