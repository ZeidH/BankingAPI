package nl.Inholland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import nl.Inholland.App;
import nl.Inholland.enumerations.AccountStatusEnum;
import nl.Inholland.model.Accounts.Account;
import nl.Inholland.model.Accounts.CurrentAccount;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccountControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Before // If everything fails, login is broken.
    public void setupLogin() throws IOException {
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<String>("username=bart&password=1234", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Login"),
                HttpMethod.POST, entity, String.class);
        Map<String, String> data = new ObjectMapper().readValue(response.getBody(), HashMap.class);
        headers.add("Authorization", "Bearer " + data.get("token"));
        headers.remove("Content-Type");
    }

    @Test
    public void whenGivenUsernameReturnRelatedAccounts() throws ParseException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Customer/bartS/Accounts"),
                HttpMethod.GET, entity, String.class);

        JSONParser helper = new JSONParser();
        List<JSONObject> accounts = (List<JSONObject>) helper.parse(response.getBody());
        TestCase.assertEquals(1, accounts.size());
    }

    @Test
    public void whenGivenPathIdReturnAccount() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Accounts/1"),
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void whenGivenAccountIdStatusIsChanged() throws ParseException, IOException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Accounts/1"),
                HttpMethod.PUT, entity, String.class);

        ResponseEntity<String> verify = restTemplate.exchange(
                createURLWithPort("/Employee/Accounts/1"),
                HttpMethod.GET, entity, String.class);

        JSONParser helper = new JSONParser();
        org.json.simple.JSONObject accounts = (org.json.simple.JSONObject) helper.parse(verify.getBody());
        assertEquals("CLOSED",accounts.get("status"));
    }
    @Test
    public void whenGivenAlreadyExistingAccountReturn406() {
        String accountJSON = "{\n" +
                "  \"countryCode\": \"NL\",\n" +
                "  \"bank\": \"INHO\",\n" +
                "  \"bban\": \"0000000007\",\n" +
                "  \"user\": \"bart\",\n" +
                "  \"name\": \"Bart's personal account\",\n" +
                "  \"balance\": \"100\",\n" +
                "  \"type\": \"NL\",\n" +
                "  \"dailyLimit\": \"1000\",\n" +
                "  \"interestRate\": \"1.5\"\n" +
                "}";

        headers.add("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(accountJSON, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Accounts"),
                HttpMethod.POST, entity, String.class);

        TestCase.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        headers.remove("Content-Type");

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
