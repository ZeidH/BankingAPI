package nl.Inholland.model.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionRequest {
    private String amount;
    private String creator;
    private String sender;
    private String receiver;
    private String category;
}
