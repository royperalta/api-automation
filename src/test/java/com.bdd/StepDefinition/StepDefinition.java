package com.bdd.StepDefinition;

import com.bdd.Step.ApiStep;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinition {

    ApiStep apiStep;

    @Before
    public void beforeScenario(){ apiStep = new ApiStep();}

    @Given("config the header")
    public void configTheHeader(DataTable dataTable) {
        apiStep.configHeaders(dataTable);
    }

    @And("config query params")
    public void configQueryParams(DataTable dataTable) {
        apiStep.configQueryParams(dataTable);
    }

    @And("config paths variables")
    public void configPathsVariables(DataTable datatable) {
        apiStep.configPathVariables(datatable);
    }

    @When("execute API")
    public void executeAPI(DataTable dataTable) {
        apiStep.executeAPI(dataTable);
    }

    @Then("check status code {string}")
    public void checkStatusCode(String code) {
        apiStep.checkStatus(code);
    }

    @And("check the response of service")
    public void checkTheResponseOfService(DataTable dataTable) {
        apiStep.checkResponse(dataTable);
    }

    @And("configure the body request: {string}")
    public void configureTheBodyRequest(String path,DataTable dataTable) {
        apiStep.configureBodyRequest(path,dataTable);
    }
}
