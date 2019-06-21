package nl.Inholland.model.requests;

public class IbanRequest {
    private String countryCode;
    private String bank;
    private String bban = null;

    public IbanRequest(String countryCode, String bank, String bban) {
        this.countryCode = countryCode;
        this.bank = bank;
        this.bban = bban;
    }
}
