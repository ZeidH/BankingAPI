package nl.Inholland.model.Accounts;

import lombok.NoArgsConstructor;
import nl.Inholland.model.requests.AccountRequest;

public interface AccountFactory {
    Account createAccount(AccountRequest request);
}
