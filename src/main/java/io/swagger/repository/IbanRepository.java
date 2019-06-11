package io.swagger.repository;

import io.swagger.model.Account;
import io.swagger.model.Iban;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IbanRepository extends JpaRepository<Iban, Long> {

    boolean existsByIbanCode(String ibanCode);
}
