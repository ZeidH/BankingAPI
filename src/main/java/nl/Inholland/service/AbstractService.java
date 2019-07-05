package nl.Inholland.service;

import com.google.common.base.Joiner;
import nl.Inholland.QueryBuilder.GenericSpecificationsBuilder;
import nl.Inholland.QueryBuilder.SearchOperation;
import nl.Inholland.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Abstract service has an instance of all repositories to be used by different services
 * for increased flexibility
 */

@Service
public abstract class AbstractService{

    protected final UserRepository userRepo;
    protected final TransactionRepository tranRepo;
    protected final AccountRepository accoRepo;
    protected final IbanRepository ibanRepo;


    protected VaultObserver vault;

    @Autowired
    public AbstractService(UserRepository userRepo, TransactionRepository tranRepo, AccountRepository accoRepo, IbanRepository ibanRepo) {
        this.userRepo = userRepo;
        this.tranRepo = tranRepo;
        this.accoRepo = accoRepo;
        this.ibanRepo = ibanRepo;
    }


    //Define pattern for all search functions, generic covers all usages of specificationsbuilder
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
