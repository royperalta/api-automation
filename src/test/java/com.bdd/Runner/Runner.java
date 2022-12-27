package com.bdd.Runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "json:target/build/cucumber.json",
        features = "src/test/resources/features",
        stepNotifications = true,
        glue = "com.bdd.StepDefinition",
        tags = " @POST_LOGIN_UNSUCCESSFULL"
)

public class Runner {
}
