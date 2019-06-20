package nl.Inholland.repository;

import nl.Inholland.model.Accounts.Iban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IbanRepository extends JpaRepository<Iban, Long> {

    boolean existsByIbanCode(String ibanCode);
}
