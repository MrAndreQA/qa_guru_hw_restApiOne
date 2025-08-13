package tests;

import models.register.RegisterBodyModel;
import models.register.RegisterResponseModel;
import models.register.MissingPasswordInRegisterModel;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static specs.BaseSpecRestApi.requestSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BaseSpecRestApi.responseSpec;

@DisplayName("Reqres.in - регистрация пользователя")
@Tag("reqres_in")
@Feature("Reqres.in тесты: Регистрация нового пользователя")
@Story("Тестирование api сайта: Reqres.in")
@Owner("Volodin_AS")
public class RegisterUserTests extends ApiTestBase {

    public int minTokenLength = 16;

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Post - Single user: Успешная регистрация нового пользователя")
    void successfulRegisterWithSpecsTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail(USER_EMAIL);
        registerData.setPassword(USER_PASSWORD);

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
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Post - Single user: Попытка регистрации пользователя - без указания пароля")
    void missingPasswordRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail(USER_EMAIL);
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
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Post - Single user: Попытка регистрации пользователя - без указания email")
    void missingEmailRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setPassword(USER_EMAIL);
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