package org.k9m.rental.it.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.k9m.rental.api.model.Customer;
import org.k9m.rental.it.steps.util.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class CustomerSteps {

    @Autowired
    private TestClient testClient;

    @Autowired
    private TestContext ctx;


    @When("a new Customer with below details is added")
    public void aNewCustomerWithBelowDetailsIsAdded(final DataTable dataTable) {
        try {
            ctx.lastSavedCustomer = testClient.addCustomer(dataTable.<Customer>asList(Customer.class).get(0));
        } catch (HttpClientErrorException e) {
            ctx.lastThrownException = e;
        }
    }

    @Then("this last saved Customer should be retrieved by its generated id")
    public void thisLastSavedCustomerShouldBeRetrievedByItsGeneratedId() {
        assertNotNull("Customer shouldn't be null", ctx.lastSavedCustomer);
        try {
            ctx.lastRetrievedCustomer = testClient.getCustomer(ctx.lastSavedCustomer.getId());
        } catch (HttpClientErrorException e) {
            ctx.lastThrownException = e;
        }
    }

    @And("the Customer details should match")
    public void theDetailsShouldMatch() {
        assertNotNull("Customer shouldn't be null", ctx.lastRetrievedCustomer);
        assertThat(ctx.lastRetrievedCustomer, is(ctx.lastSavedCustomer));
    }

    @Then("deleting this customer")
    public void deletingThisCustomer() {
        testClient.deleteCustomer(ctx.lastSavedCustomer.getId());
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
