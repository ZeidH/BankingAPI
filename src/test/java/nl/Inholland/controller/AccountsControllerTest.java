package nl.Inholland.controller;

import nl.Inholland.controller.Accounts.AccountsApiController;
import nl.Inholland.service.AccountService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountsApiController.class)
public class AccountsControllerTest {

    @Mock
    AccountService service;

    AccountsApiController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new AccountsApiController(service);
    }




}
