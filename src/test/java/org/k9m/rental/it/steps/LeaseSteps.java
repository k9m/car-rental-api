package org.k9m.rental.it.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
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
    private ObjectMapper objectMapper;

    @Autowired
    private TestContext testContext;

    @When("creating a Lease with below details")
    public void createLease(final DataTable dataTable) {
        testContext.createLease = dataTable.<CreateLease>asList(CreateLease.class).get(0);
        testContext.createLease.setCustomerId(testContext.lastSavedCustomer.getId());
        testContext.createLease.setVehicleId(testContext.lastSavedVehicle.getId());

        try {
            testContext.lastSavedLease = testClient.createLease(testContext.createLease);
        } catch (HttpClientErrorException e) {
            testContext.lastThrownException = e;
        }
    }

    @When("the created Lease should have these details")
    public void verifyLease(final DataTable dataTable) {
        Lease expected = dataTable.<Lease>asList(Lease.class).get(0);
        assertNotNull(testContext.lastSavedLease);

        assertThat(testContext.lastSavedLease.getDurationMonths(), is(expected.getDurationMonths()));
        assertThat(testContext.lastSavedLease.getInterestRate(), is(expected.getInterestRate()));
        assertThat(testContext.lastSavedLease.getMileagePerYear(), is(expected.getMileagePerYear()));
        assertThat(testContext.lastSavedLease.getStartDate(), is(expected.getStartDate()));
        assertThat(testContext.lastSavedLease.getLeaseRate(), is(expected.getLeaseRate()));
    }


    @DataTableType
    public CreateLease createLeaseEntry(Map<String, String> entry) {
        return new CreateLease()
                .startDate(LocalDate.parse(entry.get("startDate")))
                .interestRate(Double.parseDouble(entry.get("interestRate")))
                .durationMonths(Integer.parseInt(entry.get("durationMonths")))
                .mileagePerYear(Integer.parseInt(entry.get("mileagePerYear")));
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
