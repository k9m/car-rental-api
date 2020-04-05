package org.k9m.rental.it.steps;

import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;
import org.k9m.rental.it.steps.util.TestClient;
import org.k9m.rental.persistence.repository.CustomerRepository;
import org.k9m.rental.persistence.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseSteps {

    @Autowired
    private TestClient testClient;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;


    @Given("the system has started up")
    public void theSystemHasStartedUp() {
        final String healthCheckString = testClient.healthCheck();
        assertThat(healthCheckString, containsString("UP"));
    }

    @Given("and the database is empty")
    public void andTheDatabaseIsEmpty() {
        customerRepository.deleteAll();
        vehicleRepository.deleteAll();
    }

}
