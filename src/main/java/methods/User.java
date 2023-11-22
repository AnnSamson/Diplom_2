package methods;

import data.ApiUrl;
import data.UserData;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class User {

    @Step("Регистрация пользователя и получение ответа")
    public Response registerUser(UserData userData) {
        return given()
                .header("Content-type", "application/json")
                .body(userData)
                .when()
                .post(ApiUrl.REGISTER);
    }

    @Step("Удаление пользователя и получение ответа")
    public Response deleteUser(String bearerToken) {
        return given()
                .headers(
                        "Authorization", bearerToken,
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .when()
                .delete(ApiUrl.USER);
    }

    @Step("Авторизация пользователя и получение ответа")
    public Response loginUser(String json){
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(ApiUrl.LOGIN);
    }

    @Step("Обновление информации о пользователе и получение ответа")
    public Response updateUser(String bearerToken, UserData userData) {
        return given()
                .headers(
                        "Authorization", bearerToken,
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .body(userData)
                .when()
                .patch(ApiUrl.USER);
    }

    @Step("Получение списка заказов пользователя и получение ответа")
    public Response getUserOrders(String bearerToken) {
        return given()
                .headers(
                        "Authorization", bearerToken,
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .when()
                .get(ApiUrl.ORDERS);
    }
}
