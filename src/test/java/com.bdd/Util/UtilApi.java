package com.bdd.Util;

import io.cucumber.datatable.DataTable;
import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UtilApi {

    public static String getValueFromDataTable(DataTable dataTable, String title) {
        List<Map<String, String>> list = dataTable.asMaps();
        return (String) ((Map) list.get(0)).get(title);
    }

    public static Headers configurerHeaders(DataTable dataTable) {
        List<Header> headerList = new LinkedList();
        List<Map<String, String>> listCabeceras = dataTable.asMaps(String.class, String.class);

        listCabeceras.forEach( map -> {
            String nameHeaders = map.get("headers");
            String value = map.get("value");

            Header header = new Header(nameHeaders , value);
            //Content-Type=application/json
            headerList.add(header);
        });

        Headers headers = new Headers(headerList);
        return headers;
    }

}
