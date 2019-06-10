package io.swagger.repository;

import io.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User where dateCreated between (CASE WHEN ?1 IS NULL THEN CURRENT_DATE ELSE ?1 END) and (CASE WHEN ?2 IS NULL THEN CURRENT_DATE ELSE ?2 END)", nativeQuery = true)
    List<User> getUsersByDate(Date from, Date to);

}
