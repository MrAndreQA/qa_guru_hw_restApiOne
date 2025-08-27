package tests;

import models.singleUser.SingleUserResponseModel;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static specs.BaseSpecRestApi.requestSpec;
import static specs.BaseSpecRestApi.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReqresIn - Тесты на поиск пользователя")
@Tag("reqres_in")
@Feature("Reqres.in тесты: Поиск зарегистрированного пользователя")
@Story("Тестирование api сайта: Reqres.in")
@Owner("Volodin_AS")
public class SingleUserTests extends ApiTestBase {

    public int validUserId = 2;
    public int notValidUserId = 999;
    public String expectedEmail = "janet.weaver@reqres.in";


    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Метод Get: Поиск уже зарегистрированного пользователя")
    void successfulReceivingSingleUserDataTest() {
        SingleUserResponseModel userResponse = step("Отправляем запрос", () ->
                given(requestSpec)
                        .pathParam("userId", validUserId)
                .when()
                        .get(USERS_ENDPOINT + "{userId}")

                .then()
                        .spec(responseSpec(200))
                        .extract().as(SingleUserResponseModel.class));
        step("Проверяем Id в ответе", () -> {
            Integer id = userResponse.getData().getId();
            assertThat(id)
                    .as("Id не должен быть null и должен соответствовать ожидаемому")
                    .isNotNull()
                    .isEqualTo(validUserId);
        });
        step("Проверяем email в ответе", () -> {
            String email = userResponse.getData().getEmail();
            assertThat(email)
                    .as("Email не должен быть null и должен соответствовать ожидаемому")
                    .isNotNull()
                    .isEqualTo(expectedEmail);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Метод Get: Поиск пользователя - при указании несуществующего UserId")
    public void singleUserNotFoundTest() {
        String responseBody = step("Отправляем запрос", () ->
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
        step("Проверяем, что тело ответа - пустое", () -> {
            assertThat(responseBody)
                    .as("Тело ответа должно быть пустым JSON: {}")
                    .isEqualTo("{}");
        });
    }
}