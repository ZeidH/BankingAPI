package nl.Inholland.repository;

import nl.Inholland.model.Accounts.Iban;
import nl.Inholland.model.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query(value = "select u from User where dateCreated between (CASE WHEN ?1 IS NULL THEN CURRENT_DATE ELSE ?1 END) and (CASE WHEN ?2 IS NULL THEN CURRENT_DATE ELSE ?2 END)", nativeQuery = true)
    List<User> getUsersByDate(Date from, Date to);

    Optional<User> findByUsername(String username);
    User getUserByUsername(String username);

    User findByIbanList(Iban iban);
}
