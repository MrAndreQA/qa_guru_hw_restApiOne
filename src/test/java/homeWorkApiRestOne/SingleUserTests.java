package homeWorkApiRestOne;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SingleUserTests extends ApiTestBase {

    public int validUserId = 2;
    public int notValidUserId = 999;
    public String expectedEmail = "janet.weaver@reqres.in";


    @Test
    public void successfulReceivingSingleUserDataTest() {

        given()
                .log().uri()
                .log().method()
                .log().body()
                .header("x-api-key", apiKey)
                .when()
                .get(usersEndpoint + validUserId)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is(expectedEmail));
    }

    @Test
    public void singleUserNotFoundTest() {

        given()
                .log().uri()
                .log().method()
                .log().body()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get(usersEndpoint + notValidUserId)
                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .body(equalTo("{}"));
        //др. вариант .body("size()", equalTo(0));
    }
}