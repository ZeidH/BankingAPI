package nl.Inholland.model.requests;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountRequest {

    private String iban;
    private String name;
    private String balance;
    private String type;

    public AccountRequest(String iban, String name, String balance, String type) {
        this.iban = iban;
        this.name = name;
        this.balance = balance;
        this.type = type;
    }
}
