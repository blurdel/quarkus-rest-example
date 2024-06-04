package com.blurdel.rest.controllers;

import com.blurdel.rest.model.Person;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class PersonControllerTest {

    private static final String URL = "/person";


    @Test
    void testGetList() {
        given()
                .when()
                .get(URL)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testGetOne() {
        final Person postItem = given()
                .body(new Person(null, "Test Get One", 42))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .post(URL)
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().as(Person.class);

        given()
                .pathParam("id", postItem.id())
                .when()
                .get(URL + "/{id}")
                .then()

                .log().ifValidationFails(LogDetail.BODY)

                .statusCode(Response.Status.OK.getStatusCode())
//                .body("id", equalTo(postItem.id())) // WTF
                .body("name", equalTo(postItem.name()))
                .body("age", equalTo(postItem.age()));
    }

    @Test
    void testGetOneFail() {
        given()
                .pathParam("id", 0L)
                .when()
                .get(URL + "/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testPost() {
        final Person postItem = given()
                .body(new Person(null, "Test Post", 123))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .post(URL)
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().as(Person.class);

        assertNotNull(postItem.id());
        assertEquals("Test Post", postItem.name());
        assertEquals(123, postItem.age());
    }

    @Test
    public void testUpdate() {
        final Person postItem = given()
                .body(new Person(null, "Test Update", 999))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .post(URL)
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().as(Person.class);

        final Person finalItem = given()
                .pathParam("id", postItem.id())
                .body(new Person(postItem.id(), "Updated Person", 456))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .put(URL + "/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(Person.class);

        assertEquals(postItem.id(), finalItem.id());
        assertEquals("Updated Person", finalItem.name());
        assertEquals(456, finalItem.age());
    }

    @Test
    public void test() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Integer.parseInt("1a");
        });

        String expectedMessage = "For input string";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

//    @Test
//    public void testUpdateFail() {
//
//        BadRequestException thrown = assertThrows(BadRequestException.class,
//                () -> {
//                    Response resp = given()
//                            .pathParam("id", 10L)
//                            .body(new Person(11L, "Test Bad Update", 666))
//                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//                            .when()
//                            .put(URL + "/{id}")
//                            .then()
//                            .statusCode(Response.Status.OK.getStatusCode())
//                            .extract().as(Response.class);
//
//                    System.out.println(resp.toString());
//                }
//        );
//
//        assertTrue(thrown.getMessage().contains("pathParam.id"));
//    }

    @Test
    public void testDelete() {
        final Person postItem = given()
                .body(new Person(null, "Test Delete", 999))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .post(URL)
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().as(Person.class);

        given()
                .pathParam("id", postItem.id())
                .when()
                .delete(URL + "/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
//                .body("id", equalTo(postItem.id().toString())) // WTF (b/c record vs Object??)
                .body("name", equalTo(postItem.name()));
    }

    @Test
    public void testDeleteFail() {
        given()
                .pathParam("id", 0L)
                .when()
                .delete(URL + "/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

}