package org.k9m.rental.it.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.k9m.rental.api.model.CreateLease;
import org.k9m.rental.api.model.Lease;
import org.k9m.rental.it.steps.util.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class LeaseSteps {

    @Autowired
    private TestClient testClient;

    @Autowired
    private TestContext ctx;

    @When("creating a Lease with below details")
    public void createLease(final DataTable dataTable) {
        ctx.createLease = dataTable.<CreateLease>asList(CreateLease.class).get(0);
        ctx.createLease.setCustomerId(ctx.lastSavedCustomer.getId());
        ctx.createLease.setVehicleId(ctx.lastSavedVehicle.getId());

        try {
            ctx.lastSavedLease = testClient.createLease(ctx.createLease);
        } catch (HttpClientErrorException e) {
            ctx.lastThrownException = e;
        }
    }

    @When("the created Lease should have these details")
    public void verifyLease(final DataTable dataTable) {
        Lease expected = dataTable.<Lease>asList(Lease.class).get(0);
        assertNotNull(ctx.lastSavedLease);

        assertThat(ctx.lastSavedLease.getDurationMonths(), is(expected.getDurationMonths()));
        assertThat(ctx.lastSavedLease.getInterestRate(), is(expected.getInterestRate()));
        assertThat(ctx.lastSavedLease.getMileagePerYear(), is(expected.getMileagePerYear()));
        assertThat(ctx.lastSavedLease.getStartDate(), is(expected.getStartDate()));
        assertThat(ctx.lastSavedLease.getLeaseRate(), is(expected.getLeaseRate()));
    }


    @DataTableType
    public CreateLease createLeaseEntry(Map<String, String> entry) {
        return new CreateLease()
                .startDate(LocalDate.parse(entry.get("startDate")))
                .interestRate(Double.parseDouble(entry.get("interestRate")))
                .durationMonths(Integer.parseInt(entry.get("durationMonths")))
                .mileagePerYear(Integer.parseInt(entry.get("mileagePerYear")));
    }

    @Then("this last saved Lease should be retrieved by its generated id")
    public void thisLastSavedVehicleShouldBeRetrievedByItsGeneratedId() {
        assertNotNull("Lease shouldn't be null", ctx.lastSavedLease);
        try {
            ctx.lastRetrievedLease = testClient.getLease(ctx.lastSavedLease.getId());
        } catch (HttpClientErrorException e) {
            ctx.lastThrownException = e;
        }
    }

    @Then("deleting this lease")
    public void deletingThisLease() {
        testClient.deleteLease(ctx.lastSavedLease.getId());
    }


    @DataTableType
    public Lease leaseEntry(Map<String, String> entry) {
        return new Lease()
                .startDate(LocalDate.parse(entry.get("startDate")))
                .interestRate(Double.parseDouble(entry.get("interestRate")))
                .leaseRate(Double.parseDouble(entry.get("leaseRate")))
                .durationMonths(Integer.parseInt(entry.get("durationMonths")))
                .mileagePerYear(Integer.parseInt(entry.get("mileagePerYear")));
    }
}
