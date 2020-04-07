package org.k9m.rental.it.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.k9m.rental.api.model.Vehicle;
import org.k9m.rental.it.steps.util.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class VehicleSteps {

    @Autowired
    private TestClient testClient;

    @Autowired
    private TestContext ctx;


    @When("a new Vehicle with below details is added")
    public void aNewVehicleWithBelowDetailsIsAdded(final DataTable dataTable) {
        try {
            ctx.lastSavedVehicle = testClient.addVehicle(dataTable.<Vehicle>asList(Vehicle.class).get(0));
        } catch (HttpClientErrorException e) {
            ctx.lastThrownException = e;
        }
    }

    @Then("this last saved Vehicle should be retrieved by its generated id")
    public void thisLastSavedVehicleShouldBeRetrievedByItsGeneratedId() {
        assertNotNull("Vehicle shouldn't be null", ctx.lastSavedVehicle);
        try {
            ctx.lastRetrievedVehicle = testClient.getVehicle(ctx.lastSavedVehicle.getId());
        } catch (HttpClientErrorException e) {
            ctx.lastThrownException = e;
        }
    }

    @And("the Vehicle details should match")
    public void theDetailsShouldMatch() {
        assertNotNull("Vehicle shouldn't be null", ctx.lastRetrievedVehicle);
        assertThat(ctx.lastRetrievedVehicle, is(ctx.lastSavedVehicle));
    }

    @Then("deleting this Vehicle")
    public void deletingThisVehicle() {
        testClient.deleteVehicle(ctx.lastSavedVehicle.getId());
    }


    @DataTableType
    public Vehicle vehicleEntry(Map<String, String> entry) {
        return new Vehicle()
                .make(entry.get("model"))
                .model(entry.get("model"))
                .version(entry.get("version"))
                .doors(Integer.parseInt(entry.get("doors")))
                .grossPrice(Double.parseDouble(entry.get("grossPrice")))
                .nettPrice(Double.parseDouble(entry.get("nettPrice")))
                .hp(Integer.parseInt(entry.get("hp")));
    }
}
