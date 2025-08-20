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

@DisplayName("ReqresIn - Тесты на регистрацию пользователя")
@Tag("reqres_in")
@Feature("Reqres.in тесты: Регистрация нового пользователя")
@Story("Тестирование api сайта: Reqres.in")
@Owner("Volodin_AS")
public class RegisterUserTests extends ApiTestBase {

    public int minTokenLength = 16;

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Метод Post: Успешная регистрация нового пользователя")
    void successfulRegisterWithSpecsTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail(USER_EMAIL);
        registerData.setPassword(USER_PASSWORD);

        RegisterResponseModel response = step("Отправляем запрос", ()->
                given(requestSpec)
                        .body(registerData)

                        .when()
                        .post(REGISTER_ENDPOINT)

                        .then()
                        .spec(responseSpec(200))
                        .extract().as(RegisterResponseModel.class));

        step("Проверяем ответ", () -> {
            String token = response.getToken();
            assertThat(token)
                    .as("Token не должен быть null")
                    .isNotNull()
                    .as("Token не должен быть пустым")
                    .isNotBlank()
                    .as("Длина токена должна быть >= %d", minTokenLength)
                    .hasSizeGreaterThanOrEqualTo(minTokenLength)
                    .as("Token должен содержать только буквенно-цифровые символы")
                    .matches("^[a-zA-Z0-9]+$");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Метод Post: Попытка регистрации пользователя - без указания пароля")
    void missingPasswordRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail(USER_EMAIL);
        MissingPasswordInRegisterModel response = step("Отправляем запрос", () ->
                given(requestSpec)
                        .body(registerData)
                        .post(REGISTER_ENDPOINT)
                        .then()
                        .spec(responseSpec(400))
                        .extract().as(MissingPasswordInRegisterModel.class));
        step("Проверяем ответ", () ->
                assertThat(response.getError())
                        .as("Проверьте сообщение об ошибке при отсутствии пароля")
                        .isEqualTo("Missing password")
        );
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Метод Post: Попытка регистрации пользователя - без указания email")
    void missingEmailRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setPassword(USER_EMAIL);
        MissingPasswordInRegisterModel response = step("Отправляем запрос", () ->
                given(requestSpec)
                        .body(registerData)
                        .post(REGISTER_ENDPOINT)
                        .then()
                        .spec(responseSpec(400))
                        .extract().as(MissingPasswordInRegisterModel.class));
        step("Проверяем ответ", () ->
                assertThat(response.getError())
                        .as("Проверьте сообщение об ошибке при отсутствии email")
                        .isEqualTo("Missing email or username")
        );
    }
}