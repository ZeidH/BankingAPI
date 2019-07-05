package nl.Inholland;

import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.QueryBuilder.Specifications.UserSpecification;
import nl.Inholland.model.Users.Customer;
import nl.Inholland.model.Users.User;
import nl.Inholland.model.requests.UserRequest;
import nl.Inholland.repository.UserRepository;
import nl.Inholland.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockitoUserTest {

    @Autowired // What we're testing here is the service layer.
    private UserService service;

    @MockBean // Mocked! Doesn't really exists and won't use real data
    private UserRepository repository;

    @Test
    public void getUsersTest() {
        // This is only needed for my part, I gotta have a specification object to perform my .findAll on user
        String search = "";
        Specification<User> spec = service.getBuilder(search).build(searchCriteria -> new UserSpecification((SpecSearchCriteria) searchCriteria));

        // the UserRepository here is mocked, it doesn't really exist and won't use the data in the devBootstrap
        // Therefore we must mock its behavior, so the line below is basically saying > When this method is accessed..
        when(repository.findAll(spec)).thenReturn(Stream
                // ..I shall return <A list of Users> in this case.
                .of(new Customer("Bart", "fried","stefano@gmail.com", "1234566", "bart", "1234", "9-6-2019", "8-6-2019")).collect(Collectors.toList()));

        // And then you call the real access point for the repository which is through the service, to check if it works
        assertEquals(1, service.getUsers("").size());
    }

    @Test
    public void whenGivenIdReturnUser(){
        User user = new Customer("Bart", "fried","stefano@gmail.com", "1234566", "bart", "1234", "9-6-2019", "8-6-2019");
        when(repository.findById(10000001L)).thenReturn(java.util.Optional.of(user));

        // And then you call the real access point for the repository which is through the service, to check if it works
        assertEquals(user, service.getUser(10000001L));
    }

}
