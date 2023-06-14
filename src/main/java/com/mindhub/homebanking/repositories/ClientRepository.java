package com.mindhub.homebanking.repositories;
import java.util.List;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;


@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long>{
}
