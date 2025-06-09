package homeWorkApiRestOne.completedTask;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegisterUserTests extends ApiTestBase{

    public int expectedUserId = 4;
    public String expectedToken = "QpwL5tke4Pnpja7X4";

    @Test
    public void successfulRegisterTest() {
        String authBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .log().uri()
                .log().method()
                .log().body()
                .header("x-api-key", apiKey)
                .body(authBody)
                .contentType(JSON)
                .when()
                .post(registerEndpoint)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is(expectedToken))
                .body("id", is(expectedUserId));
    }

    @Test
    public void unsuccessfulRegisterWithoutPasswordTest() {
        String authBody = "{ \"email\": \"eve.holt@reqres.in\" }";
        given()
                .log().uri()
                .log().method()
                .log().body()
                .header("x-api-key", apiKey)
                .body(authBody)
                .contentType(JSON)
                .when()
                .post(registerEndpoint)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    public void unsuccessfulRegisterWithoutEmailTest() {
        String authBody = "{ \"password\": \"cityslicka\" }";
        given()
                .log().uri()
                .log().method()
                .log().body()
                .header("x-api-key", apiKey)
                .body(authBody)
                .contentType(JSON)
                .when()
                .post(registerEndpoint)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}