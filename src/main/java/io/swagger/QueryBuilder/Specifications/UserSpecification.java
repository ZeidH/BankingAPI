package io.swagger.QueryBuilder.Specifications;

import com.sun.javaws.exceptions.InvalidArgumentException;
import io.swagger.QueryBuilder.GenericSpecification;
import io.swagger.QueryBuilder.SpecSearchCriteria;
import io.swagger.model.User;

public class UserSpecification extends GenericSpecification<User> {

    public UserSpecification(SpecSearchCriteria criteria) {
        super(criteria);
    }
}
