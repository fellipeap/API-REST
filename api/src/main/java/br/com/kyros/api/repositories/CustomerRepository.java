package br.com.kyros.api.repositories;

import br.com.kyros.api.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Integer> {
    Optional<CustomerModel> findByEmail(String email);

    Optional<CustomerModel> findByCpf(String cpf);
}
