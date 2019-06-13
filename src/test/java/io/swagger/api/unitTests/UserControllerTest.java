package io.swagger.api.unitTests;

import io.swagger.QueryBuilder.Specifications.UserSpecification;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.UsersApiController;
import io.swagger.configuration.APISecurityConfig;
import io.swagger.configuration.ApplicationContextProvider;
import io.swagger.model.Account;
import io.swagger.model.CurrentAccount;
import io.swagger.model.Iban;
import io.swagger.model.User;
import io.swagger.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest extends AccountControllerTest{

    @MockBean private UserService service;
    private User user = new User();

    @Override
    @Before
    public void setUp() {
        super.setUp();
        user = new User("Bart", "fried","potato@hotmail.com", "1234566", "bart", "1234", "9-6-2019", "8-6-2019", accounts);

    }
    @Test
    public void givenUser_UserShouldRegister(){

        service.registerUser(user);

        assertEquals(user,service.getUser(user.getId()));
    }

    @Test
    public void givenFirstName_whenGettingListOfUsers_thenCorrect(){
        List<User> results = service.getUsers("firstName:Bart");

        assertThat(user, isIn(results));
    }
    @Test
    public void givenLastName_whenGettingListOfUsers_thenCorrect(){
        List<User> results = service.getUsers("lastName:fried");

        assertThat(user,isIn(results));
    }

    @Test
    public void givenPartialEmail_whenGettingListOfUsers_thenCorrect(){
        List<User> results = service.getUsers("email:potato");

        assertThat(user, isIn(results));
    }



}
