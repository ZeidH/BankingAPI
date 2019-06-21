package nl.Inholland.repository;

import nl.Inholland.model.Accounts.Iban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IbanRepository extends JpaRepository<Iban, String> {

  /*  @Query("SELECT CASE WHEN EXISTS (\n" +
            "    SELECT *\n" +
            "    FROM Iban \n" +
            "    WHERE ibanCode = ?1\n" +
            ")\n" +
            "THEN CAST(1 AS BIT)\n" +
            "ELSE CAST(0 AS BIT) END")
    boolean existsByIbanCode(String ibanCode); */
}
