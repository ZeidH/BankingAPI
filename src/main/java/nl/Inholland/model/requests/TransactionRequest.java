package nl.Inholland.model.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.Inholland.enumerations.CategoryEnum;

@Data
@NoArgsConstructor
public class TransactionRequest {
    private String amount;
    private String creator;
    private String sender;
    private String receiver;
    private String category = "OTHER";
    private String type;
    private String date;

    public TransactionRequest(String amount, String creator, String sender, String receiver, String category, String date) {
        this.amount = amount;
        this.creator = creator;
        this.sender = sender;
        this.receiver = receiver;
        this.category = category;
        this.date = date;
    }
}
