package com.blurdel.rest.controllers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PersonControllerTest {

    @Test
    void testHello() {
        given()
                .when().get("/person")
                .then()
                .statusCode(200)
                .body(is("Hello blurdel"));
    }

}