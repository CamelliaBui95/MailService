package fr.btn.repositories;

import fr.btn.entities.MailEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MailRepository implements PanacheRepositoryBase<MailEntity, Integer> {
}
