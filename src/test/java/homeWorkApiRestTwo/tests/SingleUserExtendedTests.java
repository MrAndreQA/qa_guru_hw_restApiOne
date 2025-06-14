package homeWorkApiRestTwo.tests;

import homeWorkApiRestTwo.models.singleUser.SingleUserResponseModel;
import org.junit.jupiter.api.Test;
import static homeWorkApiRestTwo.specs.BaseSpec.requestSpec;
import static homeWorkApiRestTwo.specs.BaseSpec.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class SingleUserExtendedTests extends ApiTestBase {

    public int validUserId = 2;
    public int notValidUserId = 999;
    public String expectedEmail = "janet.weaver@reqres.in";


    @Test
    void successfulReceivingSingleUserDataTest() {
        SingleUserResponseModel userResponse = step("Make request", () ->
                given(requestSpec)
                        .pathParam("userId", validUserId)
                .when()
                        .get(USERS_ENDPOINT + "{userId}")

                .then()
                        .spec(responseSpec(200))
                        .extract().as(SingleUserResponseModel.class));
        step("Check Id in response", () -> {
            Integer id = userResponse.getData().getId();
            assertThat(id)
                    .as("Id should not be null and must match request")
                    .isNotNull()
                    .isEqualTo(validUserId);
        });
        step("Check email in response", () -> {
            String email = userResponse.getData().getEmail();
            assertThat(email)
                    .as("Email should not be null and must match expected")
                    .isNotNull()
                    .isEqualTo(expectedEmail);
        });
    }

    @Test
    public void singleUserNotFoundTest() {
        String responseBody = step("Make request", () ->
                given(requestSpec)
                        .pathParam("userId", notValidUserId)
                        .when()
                        .get(USERS_ENDPOINT + "{userId}")

                        .then()
                        .spec(responseSpec(404))
                        .extract()
                        .body()
                        .asString()
        );
        step("Check response body is empty", () -> {
            assertThat(responseBody)
                    .as("Response body should be empty JSON: {}")
                    .isEqualTo("{}");
        });
    }
}