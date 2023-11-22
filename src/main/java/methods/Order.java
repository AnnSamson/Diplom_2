package methods;

import data.ApiUrl;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Order {
    @Step("Создание заказа и получение ответа")
    public Response createOrder(String bearerToken, String ingredients) {
        return given()
                .header("Content-type", "application/json")
                .headers("Authorization", bearerToken,
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .body(ingredients)
                .when()
                .post(ApiUrl.ORDERS);
    }
}
