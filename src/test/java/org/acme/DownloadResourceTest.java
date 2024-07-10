package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class DownloadResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
            .when().post("/download/create")
            .then()
            .statusCode(204);

        given()
            .when().get("/download")
            .then()
            .statusCode(200);

        given()
            .when().delete("/download/delete")
            .then()
            .statusCode(204);
    }

}
