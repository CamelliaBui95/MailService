package fr.btn.repositories;

import fr.btn.entities.ClientEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;


@RequestScoped
public class ClientRepository implements PanacheRepositoryBase<ClientEntity, Integer> {
    public ClientEntity findClientByApiKey(String apiKey) {
        if(list("apiKey = ?1", apiKey).size() == 0)
            return null;

        return list("apiKey = ?1",apiKey).get(0);
    }
}
