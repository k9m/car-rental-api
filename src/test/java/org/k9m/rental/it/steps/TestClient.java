package org.k9m.rental.it.steps;

import lombok.Getter;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
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

}
