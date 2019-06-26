package nl.Inholland.repository;

import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.Iban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account>{
  /*  @Query("select a from Account a where a.iban.ibanCode = ?1")
    public Account getAccountByIban(String ibanCode); */

   // Optional<Account> findByIban(Iban iban);
    Account getAccountByIban(Iban iban);
}