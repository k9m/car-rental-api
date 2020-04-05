package org.k9m.rental.it.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.k9m.rental.api.model.ErrorObject;
import org.k9m.rental.api.model.Vehicle;
import org.k9m.rental.it.steps.util.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class VehicleSteps {

    @Autowired
    private TestClient testClient;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehicle lastSavedVehicle;
    private Vehicle lastRetrievedVehicle;
    private HttpClientErrorException lastThrownException;

    @When("a new Vehicle with below details is added")
    public void aNewVehicleWithBelowDetailsIsAdded(final DataTable dataTable) {
        try {
            lastSavedVehicle = testClient.addVehicle(dataTable.<Vehicle>asList(Vehicle.class).get(0));
        } catch (HttpClientErrorException e) {
            lastThrownException = e;
        }
    }

    @Then("this last saved Vehicle should be retrieved by its generated id")
    public void thisLastSavedVehicleShouldBeRetrievedByItsGeneratedId() {
        assertNotNull("Vehicle shouldn't be null", lastSavedVehicle);
        try {
            lastRetrievedVehicle = testClient.getVehicle(lastSavedVehicle.getId());
        } catch (HttpClientErrorException e) {
            lastThrownException = e;
        }
    }

    @And("the Vehicle details should match")
    public void theDetailsShouldMatch() {
        assertNotNull("Vehicle shouldn't be null", lastRetrievedVehicle);
        assertThat(lastSavedVehicle, is(lastRetrievedVehicle));
    }

    @Then("deleting this Vehicle")
    public void deletingThisVehicle() {
        testClient.deleteVehicle(lastSavedVehicle.getId());
    }

    @Then("^a Vehicle error should be returned with message containing: (.*) and status code: (\\d+)$")
    public void errorMsgContains(String message, int statusCode) throws JsonProcessingException {
        assertNotNull("Error shouldn't be null", lastThrownException);
        final ErrorObject errorObject = objectMapper.readValue(lastThrownException.getResponseBodyAsString(), ErrorObject.class);

        assertThat(errorObject.getMessage(), containsString(message));
        assertThat(errorObject.getStatusCode(), is(statusCode));
    }

    @DataTableType
    public Vehicle vehicleEntry(Map<String, String> entry) {
        return new Vehicle()
                .model(entry.get("model"))
                .version(entry.get("version"))
                .doors(Integer.parseInt(entry.get("doors")))
                .grossPrice(Double.parseDouble(entry.get("grossPrice")))
                .nettPrice(Double.parseDouble(entry.get("nettPrice")))
                .hp(Integer.parseInt(entry.get("hp")));
    }
}
