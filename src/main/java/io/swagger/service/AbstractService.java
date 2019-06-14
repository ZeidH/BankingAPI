package io.swagger.service;

import com.google.common.base.Joiner;
import io.swagger.QueryBuilder.*;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.IbanRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractService {

    protected final UserRepository userRepo;
    protected final TransactionRepository tranRepo;
    protected final AccountRepository accoRepo;
    protected final IbanRepository ibanRepo;

    public AbstractService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        this.userRepo = userRepo;
        this.tranRepo = tranRepo;
        this.accoRepo = accoRepo;
        this.ibanRepo = ibanRepo;
    }

    public <T> GenericSpecificationsBuilder getBuilder(T search) {
        GenericSpecificationsBuilder builder = new GenericSpecificationsBuilder();
        String operationSetExper = Joiner.on("|")
                .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }
        return builder;
    }
}
