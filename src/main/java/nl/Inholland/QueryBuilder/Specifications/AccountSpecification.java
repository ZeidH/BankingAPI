package nl.Inholland.QueryBuilder.Specifications;

import nl.Inholland.QueryBuilder.GenericSpecification;
import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.model.Accounts.Account;

public class AccountSpecification extends GenericSpecification<Account> {

    public AccountSpecification(SpecSearchCriteria criteria) {
        super(criteria);
    }
}
