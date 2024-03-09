package fr.btn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="CLIENT")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID_CLIENT")
    private Integer id;

    @Column(name="NOM_CLIENT")
    private String name;

    @Column(name="EMAIL")
    private String email;

    @Column(name="CLE")
    private String apiKey;

    @Column(name="QUOTA_MENSUEL")
    private Integer monthlyAllocation;

    @Column(name="DATE_CREE")
    private LocalDate createdDate;

    @Column(name="STATUT")
    private String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<MailEntity> mails;
}
