package org.k9m.rental.it.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.k9m.rental.api.model.ErrorObject;
import org.k9m.rental.it.steps.util.TestClient;
import org.k9m.rental.persistence.repository.CustomerRepository;
import org.k9m.rental.persistence.repository.LeaseRepository;
import org.k9m.rental.persistence.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

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

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestContext ctx;


    @Given("the system has started up")
    public void theSystemHasStartedUp() {
        final String healthCheckString = testClient.healthCheck();
        assertThat(healthCheckString, containsString("UP"));
    }

    @Given("and the database is empty")
    public void andTheDatabaseIsEmpty() {
        leaseRepository.deleteAll();
        customerRepository.deleteAll();
        vehicleRepository.deleteAll();
    }

    @Then("^an error should be returned with message containing: (.*) and status code: (\\d+)$")
    public void errorMsgContains(String message, int statusCode) throws JsonProcessingException {
        assertNotNull("Error shouldn't be null", ctx.lastThrownException);
        final ErrorObject errorObject = objectMapper.readValue(ctx.lastThrownException.getResponseBodyAsString(), ErrorObject.class);

        assertThat(errorObject.getMessage(), containsString(message));
        assertThat(errorObject.getStatusCode(), is(statusCode));
    }

}
