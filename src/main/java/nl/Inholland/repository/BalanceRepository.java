package nl.Inholland.repository;

import nl.Inholland.model.Accounts.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
