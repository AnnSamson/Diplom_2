import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.*;

@DisplayName("Тесты получения списка заказов конкретного пользователя")
public class GetOrdersUserTest extends BaseTest{
    @DisplayName("Получение заказов авторизованного пользователя")
    @Test
    public void checkGetOrdersWithAuth() {
        Response response = user.registerUser(userData);
        bearerToken = response.then().extract().path("accessToken");

        Response ordersResponse = user.getUserOrders(bearerToken);
        ordersResponse
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("orders", notNullValue())
                .body("total", notNullValue())
                .body("totalToday", notNullValue());
    }

    @DisplayName("Получение заказов не авторизованного пользователя")
    @Test
    public void checkGetOrdersWithoutAuth() {
        bearerToken = "no_token";

        Response ordersResponse = user.getUserOrders(bearerToken);
        ordersResponse
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
