package nl.Inholland.model.requests;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountRequest {

    private String countryCode;
    private String bank;
    private String bban = null;
    private String user;
    private String name;
    private String balance;
    private String type;

}
