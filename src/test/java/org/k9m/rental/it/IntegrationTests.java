package org.k9m.rental.it;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
@Slf4j
public class IntegrationTests {

  @BeforeClass
  public static void setup() {
    log.info("Ran before tests");
  }

  @AfterClass
  public static void teardown() {
    log.info("Ran after tests");
  }

}