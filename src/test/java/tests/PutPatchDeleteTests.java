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
    @DisplayName("Delete - Single user: Успешное удаление пользователя")
    void successfulDeleteUserTest() {
        step("Make request: Delete User and check status code", () ->
                given(requestSpec)
                        .pathParam("userId", validUserId)
                .when()
                        .delete(USERS_ENDPOINT + "{userId}")

                .then()
                        .spec(responseSpec(204)));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("PUT - Single user: Успешное изменение данных о пользователе")
    void successfulPutUserTest() {
        UserBodyModel userPutData = new UserBodyModel();
        userPutData.setName("Afina");
        userPutData.setJob("Queen");

        UserResponseModel response = step("Make Put request", () ->
                given(requestSpec)
                        .pathParam("userId", validUserId)
                        .body(userPutData)

                        .when()
                        .put(USERS_ENDPOINT + "{userId}")

                        .then()
                        .spec(responseSpec(200))
                        .extract().as(UserResponseModel.class));
        step("Check Name in response body", () ->
                assertThat(response.getName())
                        .as("Check Name")
                        .isEqualTo(userPutData.getName())
        );
        step("Check Job in response body", () ->
                assertThat(response.getJob())
                        .as("Check Name")
                        .isEqualTo(userPutData.getJob())
        );
        step("Check updatedAt timestamp", () -> {
            // Проверяем, что поле существует
            assertThat(response.getUpdatedAt())
                    .as("updatedAt should not be null")
                    .isNotNull();
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("PATCH - Single user: Успешное изменение данных о пользователе")
    void successfulPatchUserTest() {
        UserBodyModel userPatchData = new UserBodyModel();
        userPatchData.setName("James");
        userPatchData.setJob("Cleaner");

        UserResponseModel patchResponse = step("Make Patch request", () ->
                given(requestSpec)
                        .pathParam("userId", validUserId)
                        .body(userPatchData)

                        .when()
                        .patch(USERS_ENDPOINT + "{userId}")

                        .then()
                        .spec(responseSpec(200))
                        .extract().as(UserResponseModel.class));
        step("Check Name in response body", () ->
                assertThat(patchResponse.getName())
                        .as("Check Name")
                        .isEqualTo(userPatchData.getName())
        );
        step("Check Job in response body", () ->
                assertThat(patchResponse.getJob())
                        .as("Check Name")
                        .isEqualTo(userPatchData.getJob())
        );
        step("Check updatedAt timestamp", () -> {
            assertThat(patchResponse.getUpdatedAt())
                    .as("updatedAt should not be null")
                    .isNotNull();
        });
    }
}