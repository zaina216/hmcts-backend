package uk.gov.hmcts.reform.dev.controllers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmokeTest {

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void appShouldStartAndRespond() {
        Response response = RestAssured
            .given()
            .when()
            .get("/api/tasks/get-all-tasks")
            .then()
            .extract()
            .response();

        assertEquals(200, response.statusCode());
    }
}
