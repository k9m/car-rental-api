package org.k9m.rental.it.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.k9m.rental.api.model.Customer;
import org.k9m.rental.api.model.ErrorObject;
import org.k9m.rental.it.steps.util.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class CustomerSteps {

    @Autowired
    private TestClient testClient;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer lastSavedCustomer;
    private Customer lastRetrievedCustomer;
    private HttpClientErrorException lastThrownException;


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

    @And("the Customer details should match")
    public void theDetailsShouldMatch() {
        assertNotNull("Customer shouldn't be null", lastRetrievedCustomer);
        assertThat(lastSavedCustomer, is(lastRetrievedCustomer));
    }

    @Then("deleting this customer")
    public void deletingThisCustomer() {
        testClient.deleteCustomer(lastSavedCustomer.getId());
    }

    @Then("^a Customer error should be returned with message containing: (.*) and status code: (\\d+)$")
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
