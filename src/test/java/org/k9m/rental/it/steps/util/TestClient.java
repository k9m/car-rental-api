package org.k9m.rental.it.steps.util;

import lombok.Getter;
import org.k9m.rental.api.model.CreateLease;
import org.k9m.rental.api.model.Customer;
import org.k9m.rental.api.model.Lease;
import org.k9m.rental.api.model.Vehicle;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class TestClient {

    @LocalServerPort
    @Getter
    private int serverPort;

    private RestTemplate restTemplate = new RestTemplate();
    private String baseUrl;

    @PostConstruct
    private void init(){
        baseUrl = "http://localhost:" + serverPort;
    }

    public String healthCheck(){
        return restTemplate.getForObject(baseUrl + "/actuator/health", String.class);
    }

    public Customer addCustomer(Customer customer){
        return restTemplate.exchange(baseUrl + "/customers", HttpMethod.PUT, new HttpEntity<>(customer), Customer.class).getBody();
    }

    public Customer getCustomer(Long customerId){
        return restTemplate.getForObject(baseUrl + "/customers/" + customerId, Customer.class);
    }

    public void deleteCustomer(Long customerId){
        restTemplate.delete(baseUrl + "/customers/" + customerId);
    }


    public Vehicle addVehicle(Vehicle vehicle){
        return restTemplate.exchange(baseUrl + "/vehicles", HttpMethod.PUT, new HttpEntity<>(vehicle), Vehicle.class).getBody();
    }

    public Vehicle getVehicle(Long vehicleId){
        return restTemplate.getForObject(baseUrl + "/vehicles/" + vehicleId, Vehicle.class);
    }

    public void deleteVehicle(Long vehicleId){
        restTemplate.delete(baseUrl + "/vehicles/" + vehicleId);
    }


    public Lease createLease(CreateLease createLease){
        return restTemplate.exchange(baseUrl + "/lease", HttpMethod.PUT, new HttpEntity<>(createLease), Lease.class).getBody();
    }

}
