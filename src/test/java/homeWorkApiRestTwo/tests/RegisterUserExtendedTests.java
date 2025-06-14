package homeWorkApiRestTwo.tests;

import homeWorkApiRestTwo.models.register.RegisterBodyModel;
import homeWorkApiRestTwo.models.register.RegisterResponseModel;
import homeWorkApiRestTwo.models.register.MissingPasswordInRegisterModel;
import org.junit.jupiter.api.Test;
import static homeWorkApiRestTwo.specs.BaseSpec.*;
import static homeWorkApiRestTwo.specs.BaseSpec.requestSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RegisterUserExtendedTests extends ApiTestBase {

    public int minTokenLength = 16;

    @Test
    void successfulRegisterWithSpecsTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("cityslicka");

        RegisterResponseModel response = step("Make request", ()->
                given(requestSpec)
                        .body(registerData)

                        .when()
                        .post(REGISTER_ENDPOINT)

                        .then()
                        .spec(responseSpec(200))
                        .extract().as(RegisterResponseModel.class));

        step("Check response", () -> {
            String token = response.getToken();
            assertThat(token)
                    .as("Token should not be null")
                    .isNotNull()
                    .as("Token should not be blank")
                    .isNotBlank()
                    .as("Token length should be >= %d", minTokenLength)
                    .hasSizeGreaterThanOrEqualTo(minTokenLength)
                    .as("Token should contain only alphanumeric characters")
                    .matches("^[a-zA-Z0-9]+$");
        });
    }

    @Test
    void missingPasswordRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("eve.holt@reqres.in");
        MissingPasswordInRegisterModel response = step("Make request", () ->
                given(requestSpec)
                        .body(registerData)
                        .post(REGISTER_ENDPOINT)
                        .then()
                        .spec(responseSpec(400))
                        .extract().as(MissingPasswordInRegisterModel.class));
        step("Check response", () ->
                assertThat(response.getError())
                        .as("Check error message for missing password")
                        .isEqualTo("Missing password")
        );
    }

    @Test
    void missingEmailRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setPassword("cityslicka");
        MissingPasswordInRegisterModel response = step("Make request", () ->
                given(requestSpec)
                        .body(registerData)
                        .post(REGISTER_ENDPOINT)
                        .then()
                        .spec(responseSpec(400))
                        .extract().as(MissingPasswordInRegisterModel.class));
        step("Check response", () ->
                assertThat(response.getError())
                        .as("Check error message for missing email")
                        .isEqualTo("Missing email or username")
        );
    }
}