package io.swagger.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
//
//    @Query("select t from Transaction t, Account a where t.creator or t.receiver or t.sender = a.iban")
//    List<Transaction> getTransactionsByUserName();
}
