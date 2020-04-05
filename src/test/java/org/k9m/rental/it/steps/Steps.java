package org.k9m.rental.it.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.k9m.rental.api.model.Customer;
import org.k9m.rental.api.model.ErrorObject;
import org.k9m.rental.persistence.repository.CustomerRepository;
import org.k9m.rental.persistence.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class Steps {

    @Autowired
    private TestClient testClient;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer lastSavedCustomer;
    private Customer lastRetrievedCustomer;
    private HttpClientErrorException lastThrownException;


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

    @When("a new Customer with below details is added")
    public void aNewCustomerWithBelowDetailsIsAdded(final DataTable dataTable) {
        try {
            lastSavedCustomer = testClient.addCustomer(dataTable.<Customer>asList(Customer.class).get(0));
        } catch (HttpClientErrorException e) {
            lastThrownException = e;
        }
    }

    @Then("this last saved Customer should be retrieved by its generated id")
    public void thisLastSavedCustomerShouldBeRetrievedByItsGeneratedId() {
        assertNotNull("Customer shouldn't be null", lastSavedCustomer);
        try {
            lastRetrievedCustomer = testClient.getCustomer(lastSavedCustomer.getId());
        } catch (HttpClientErrorException e) {
            lastThrownException = e;
        }
    }

    @And("the details should match")
    public void theDetailsShouldMatch() {
        assertNotNull("Customer shouldn't be null", lastRetrievedCustomer);
        assertThat(lastSavedCustomer, is(lastRetrievedCustomer));
    }

    @Then("deleting this customer")
    public void deletingThisCustomer() {
        testClient.deleteCustomer(lastSavedCustomer.getId());
    }

    @Then("^an error should be returned with message containing: (.*) and status code: (\\d+)$")
    public void errorMsgContains(String message, int statusCode) throws JsonProcessingException {
        assertNotNull("Error shouldn't be null", lastThrownException);
        final ErrorObject errorObject = objectMapper.readValue(lastThrownException.getResponseBodyAsString(), ErrorObject.class);

        assertThat(errorObject.getMessage(), containsString(message));
        assertThat(errorObject.getStatusCode(), is(statusCode));
    }

    @DataTableType
    public Customer authorEntry(Map<String, String> entry) {
        return new Customer()
                .name(entry.get("name"))
                .street(entry.get("street"))
                .houseNumber(Integer.parseInt(entry.get("houseNumber")))
                .zipcode(entry.get("zipcode"))
                .place(entry.get("place"))
                .email(entry.get("email"))
                .phoneNumber(entry.get("phoneNumber"));
    }
}
