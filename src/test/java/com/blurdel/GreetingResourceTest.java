package com.blurdel;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {

    @Test
    void testHello() {
        given()
                .when()
                .get("/hello")
                .then()
                .statusCode(200)
                .body(is("Hello!"));
    }

    @Test
    void testGreeting() {
        final String name = "Fred";

        given()
                .pathParam("name", name)
                .when()
                .get("/hello/greet/{name}")
                .then()
                .statusCode(200)
                .body(is("Hello " + name));
    }

}