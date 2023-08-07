package com.anhkhoa;

import io.restassured.RestAssured;

public class UserResourceTest {

    public void testGetUser() {
        RestAssured.baseURI = "http://localhost:8080/demorest/api";

        RestAssured.given()
                .when()
                .get("/users/v1")
                .then()
                .statusCode(200)
                .body();
    }

}
