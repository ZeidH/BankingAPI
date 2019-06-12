package io.swagger.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import io.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

    @Query(value = "select u from User where dateCreated between (CASE WHEN ?1 IS NULL THEN CURRENT_DATE ELSE ?1 END) and (CASE WHEN ?2 IS NULL THEN CURRENT_DATE ELSE ?2 END)", nativeQuery = true)
    List<User> getUsersByDate(Date from, Date to);

    Optional<User> findByUsername(String username);

    @Override
    default public void customize(final QuerydslBindings bindings, final QUser root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.excluding(root.email);
    }

}

