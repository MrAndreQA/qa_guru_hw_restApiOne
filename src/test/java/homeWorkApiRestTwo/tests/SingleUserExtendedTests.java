package homeWorkApiRestTwo.tests;

import homeWorkApiRestTwo.models.singleUser.SingleUserResponseModel;
import org.junit.jupiter.api.Test;
import static homeWorkApiRestTwo.specs.SingleUserSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class SingleUserExtendedTests {

    public int validUserId = 2;
    public int notValidUserId = 999;
    public String expectedEmail = "janet.weaver@reqres.in";


    @Test
    void successfulReceivingSingleUserDataTest() {
        SingleUserResponseModel userResponse = step("Make request", () ->
                given(singleUserRequestSpec)
                        .pathParam("userId", validUserId)
                .when()
                        .get()

                .then()
                        .spec(singleUserResponseSpec)
                        .extract().as(SingleUserResponseModel.class));

        step("Check Id in response", () -> {
            Integer id = userResponse.getData().getId();
            assertThat(id)
                    .as("Id should not be null")
                    .isNotNull()
                    .as("Id must match the Id from the request")
                    .isEqualTo(validUserId);
        });
        step("Check email in response", () -> {
            String email = userResponse.getData().getEmail();
            assertThat(email)
                    .as("Email should not be null")
                    .isNotNull()
                    .as("Email must match the Email from the request")
                    .isEqualTo(expectedEmail);
        });
    }

    @Test
    public void singleUserNotFoundTest() {
        String responseBody = step("Make request", () ->
                given(singleUserRequestSpec)
                        .pathParam("userId", notValidUserId)
                        .when()
                        .get()

                        .then()
                        .spec(notFoundInSingleUserResponseSpec)
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