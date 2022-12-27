package com.bdd.Step;

import com.bdd.Util.UtilApi;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ApiStep {
    private RequestSpecification requestSpecification;
    private Response response;
    private String bodyRequest;

    public void configHeaders(DataTable dataTable) {

        Headers headers = UtilApi.configurerHeaders(dataTable);
        Logger.getGlobal().log(Level.INFO, "HEADERS:", headers);

        requestSpecification = RestAssured.given().log().all().headers(headers);

    }

    public void configQueryParams(DataTable dataTable) {
        List<Map<String, String>> mapList = dataTable.asMaps();
        mapList.forEach(map -> {
            String key = map.get("key");
            String value = map.get("value");
            requestSpecification.queryParam(key, value);
        });
    }

    public void configPathVariables(DataTable datatable) {
        List<Map<String, String>> mapList = datatable.asMaps();
        mapList.forEach(map -> {
            String key = map.get("key");
            String value = map.get("value");
            requestSpecification.pathParam(key, value);
        });
    }

    public void executeAPI(DataTable dataTable) {
        String method = UtilApi.getValueFromDataTable(dataTable, "method");
        String url = UtilApi.getValueFromDataTable(dataTable, "url");

        switch (method) {
            case "POST":
                response = requestSpecification.body(bodyRequest).when().log().all().post(url);
                break;
            case "GET":
                response = requestSpecification.when().log().all().get(url);
                break;
            case "PUT":
                response = requestSpecification.body(bodyRequest).when().log().all().put(url);
                break;
            case "PATCH":
                response = requestSpecification.body(bodyRequest).when().log().all().patch(url);
                break;


        }
        response.prettyPeek();
    }

    public void checkStatus(String code) {
        Logger.getGlobal().log(Level.INFO, "STATUS CODE: ", code);
        int statusCode = Integer.parseInt(code);
        response.then().assertThat().statusCode(statusCode);
    }

    public void checkResponse(DataTable dataTable) {

        String character = response.asPrettyString().trim().substring(0, 1);
        List<Map<String, String>> mapList = dataTable.asMaps();
        System.out.println(mapList);

        mapList.forEach(map -> {
            String nodo = map.get("nodo");
            String valorEsperado = map.get("value");
            String valorObtenido = "";

            JsonPath resJsonPath = new JsonPath(response.getBody().asString());
            if (character.equalsIgnoreCase("{") || character.equalsIgnoreCase("[")) {
                valorObtenido = String.valueOf(resJsonPath.getString(nodo)).trim();
                Assert.assertEquals(valorEsperado, valorObtenido);
            } else {
                Assert.fail("Incorrect format");
            }
        });

    }

    public void configureBodyRequest(String path, DataTable dataTable) {
        String file = System.getProperty("user.dir") + "/src/test/resources/request.json/" + path;
        List<Map<String, String>> mapList = dataTable.asMaps();
        try {
            //read the file that contains the json
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bodyRequest = bufferedReader.lines().collect(Collectors.joining());
            System.out.println(bodyRequest);

            JsonPath resJsonPath = new JsonPath(bodyRequest);
            mapList.forEach(map -> {
                String nodo = map.get("key");
                String value = map.get("value");

                //Replace the value from the path file
                String valueJsonPath = String.valueOf(resJsonPath.getString(nodo)).trim();
                bodyRequest = bodyRequest.replace(valueJsonPath, value);
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.getGlobal().log(Level.INFO, "BODY REQUEST: {0}", bodyRequest);
    }
}
