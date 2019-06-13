package io.swagger.repository;

import io.swagger.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account>{
    @Query("select a from Account a where a.iban.ibanCode = ?1")
    public Account getAccountByIban(String ibanCode);
}