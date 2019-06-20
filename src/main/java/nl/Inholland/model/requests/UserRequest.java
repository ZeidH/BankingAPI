package nl.Inholland.model.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String dateCreated;
    private String birthday;
    private String initRole;

    public UserRequest(String firstName, String lastName, String email, String phone, String username, String password, String dateCreated, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
        this.birthday = birthday;
    }
}
