package tests;

import models.users.UserBodyModel;
import models.users.UserResponseModel;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static specs.BaseSpecRestApi.requestSpec;
import static specs.BaseSpecRestApi.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Методы: Put, Patch, Delete")
@Tag("reqres_in")
@Feature("Reqres.in тесты на различные методы")
@Story("Тестирование api сайта: Reqres.in")
@Owner("Volodin_AS")
public class PutPatchDeleteTests extends ApiTestBase {

    public int validUserId = 2;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Метод Delete: Успешное удаление пользователя")
    void successfulDeleteUserTest() {
        step("Отправка запроса: удаление пользователя и проверка кода ответа", () ->
                given(requestSpec)
                        .pathParam("userId", validUserId)
                .when()
                        .delete(USERS_ENDPOINT + "{userId}")

                .then()
                        .spec(responseSpec(204)));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Метод PUT: Успешное изменение данных о пользователе")
    void successfulPutUserTest() {
        UserBodyModel userPutData = new UserBodyModel();
        userPutData.setName("Afina");
        userPutData.setJob("Queen");

        UserResponseModel response = step("Отправка Put запроса", () ->
                given(requestSpec)
                        .pathParam("userId", validUserId)
                        .body(userPutData)

                        .when()
                        .put(USERS_ENDPOINT + "{userId}")

                        .then()
                        .spec(responseSpec(200))
                        .extract().as(UserResponseModel.class));
        step("Проверка параметра 'Name' в теле ответа", () ->
                assertThat(response.getName())
                        .as("Проверка параметра Name")
                        .isEqualTo(userPutData.getName())
        );
        step("Проверка параметра 'Job' в теле ответа", () ->
                assertThat(response.getJob())
                        .as("Проверка параметра Job")
                        .isEqualTo(userPutData.getJob())
        );
        step("Проверка параметра 'updatedAt' в теле ответа", () -> {
            assertThat(response.getUpdatedAt())
                    .as("Параметр 'updatedAt' не должен быть равен null")
                    .isNotNull();
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Метод PATCH: Успешное изменение данных о пользователе")
    void successfulPatchUserTest() {
        UserBodyModel userPatchData = new UserBodyModel();
        userPatchData.setName("James");
        userPatchData.setJob("Cleaner");

        UserResponseModel patchResponse = step("Отправка Patch запроса", () ->
                given(requestSpec)
                        .pathParam("userId", validUserId)
                        .body(userPatchData)

                        .when()
                        .patch(USERS_ENDPOINT + "{userId}")

                        .then()
                        .spec(responseSpec(200))
                        .extract().as(UserResponseModel.class));
        step("Проверка параметра 'Name' в теле ответа", () ->
                assertThat(patchResponse.getName())
                        .as("Проверка параметра Name")
                        .isEqualTo(userPatchData.getName())
        );
        step("Проверка параметра 'Job' в теле ответа", () ->
                assertThat(patchResponse.getJob())
                        .as("Проверка параметра Job")
                        .isEqualTo(userPatchData.getJob())
        );
        step("Проверка параметра 'updatedAt' в теле ответа", () -> {
            assertThat(patchResponse.getUpdatedAt())
                    .as("Параметр 'updatedAt' не должен быть равен null")
                    .isNotNull();
        });
    }
}