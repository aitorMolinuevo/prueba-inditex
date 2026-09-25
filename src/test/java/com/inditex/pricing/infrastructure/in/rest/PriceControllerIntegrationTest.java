package com.inditex.pricing.infrastructure.in.rest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void test1_requestAt10OnDay14() {
        given()
            .queryParam("applicationDate", "2020-06-14T10:00:00")
            .queryParam("productId", 35455)
            .queryParam("brandId", 1)
        .when()
            .get("/prices")
        .then()
            .statusCode(200)
            .body("price", equalTo(35.5f))
            .body("priceList", equalTo(1))
            .body("brandId", equalTo(1))
            .body("productId", equalTo(35455));
    }

    @Test
    void test2_requestAt16OnDay14() {
        given()
            .queryParam("applicationDate", "2020-06-14T16:00:00")
            .queryParam("productId", 35455)
            .queryParam("brandId", 1)
        .when()
            .get("/prices")
        .then()
            .statusCode(200)
            .body("price", equalTo(25.45f))
            .body("priceList", equalTo(2));
    }

    @Test
    void test3_requestAt21OnDay14() {
        given()
            .queryParam("applicationDate", "2020-06-14T21:00:00")
            .queryParam("productId", 35455)
            .queryParam("brandId", 1)
        .when()
            .get("/prices")
        .then()
            .statusCode(200)
            .body("price", equalTo(35.5f))
            .body("priceList", equalTo(1));
    }

    @Test
    void test4_requestAt10OnDay15() {
        given()
            .queryParam("applicationDate", "2020-06-15T10:00:00")
            .queryParam("productId", 35455)
            .queryParam("brandId", 1)
        .when()
            .get("/prices")
        .then()
            .statusCode(200)
            .body("price", equalTo(30.5f))
            .body("priceList", equalTo(3));
    }

    @Test
    void test5_requestAt21OnDay16() {
        given()
            .queryParam("applicationDate", "2020-06-16T21:00:00")
            .queryParam("productId", 35455)
            .queryParam("brandId", 1)
        .when()
            .get("/prices")
        .then()
            .statusCode(200)
            .body("price", equalTo(38.95f))
            .body("priceList", equalTo(4));
    }
}
