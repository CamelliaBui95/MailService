package fr.btn.entities;

import jakarta.persistence.*;
import jakarta.ws.rs.client.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="MAIL_HISTORY")
public class MailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_MAIL")
    private Integer id;

    @Column(name="OBJET")
    private String subject;

    @Column(name="DESTINATAIRE")
    private String recipient;

    @Column(name="DATE_ENVOI")
    private LocalDate date;

    @Column(name="HEURE_ENVOI")
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENT")
    private ClientEntity sender;
}
