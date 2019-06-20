package nl.Inholland.QueryBuilder.Specifications;

import nl.Inholland.QueryBuilder.GenericSpecification;
import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.model.Users.User;

public class UserSpecification extends GenericSpecification<User> {

    public UserSpecification(SpecSearchCriteria criteria) {
        super(criteria);
    }
}
