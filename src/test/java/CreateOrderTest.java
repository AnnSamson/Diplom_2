import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import data.Ingredients;
import data.IngredientData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import methods.Order;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты создания заказа")
public class CreateOrderTest extends BaseTest {
    @DisplayName("Создание заказа с авторизацией с ингредиентами")
    @Test
    public void checkCreateOrderWithAuthAndIngredients() {
        Response response = user.registerUser(userData);
        bearerToken = response.then().extract().path("accessToken");

        Order order = new Order();
        Ingredients ingredients = new Ingredients().getIngredients();
        // получаем объект ингредиентов
        List<IngredientData> ingredientsData = ingredients.getData();

        JsonArray ingredientsArray = new JsonArray();
        for (IngredientData ingredient : ingredientsData) {
            ingredientsArray.add(ingredient.get_id());
        }

        JsonObject ingredientsJson = new JsonObject();
        ingredientsJson.add("ingredients", ingredientsArray);

        // проверяем ответ создания заказа
        Response responseOrder = order.createOrder(bearerToken, ingredientsJson.toString());
        responseOrder
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }

    @DisplayName("Создание заказа с авторизацией без ингредиентов")
    @Test
    public void checkCreateOrderWithAuthAndWithoutIngredients() {
        Response response = user.registerUser(userData);
        bearerToken = response.then().extract().path("accessToken");

        Order order = new Order();
        // формируем JSON без ингредиентов
        JsonObject ingredientsJson = new JsonObject();
        ingredientsJson.add("ingredients", new JsonArray());
        // проверяем ответ создания заказа
        Response responseOrder = order.createOrder(bearerToken, ingredientsJson.toString());
        responseOrder
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @DisplayName("Создание заказа с авторизацией с невалидным хеш ингредиентов")
    @Test
    public void checkCreateOrderWithAuthAndWrongHashIngredients() {
        Response response = user.registerUser(userData);
        bearerToken = response.then().extract().path("accessToken");

        Order order = new Order();
        Ingredients ingredients = new Ingredients().getIngredients();
        // получаем объект ингредиентов
        List<IngredientData> ingredientsData = ingredients.getData();

        JsonArray ingredientsArray = new JsonArray();
        for (IngredientData ingredient : ingredientsData) {
            ingredientsArray.add(ingredient.get_id() + "wrongHash");
        }

        JsonObject ingredientsJson = new JsonObject();
        ingredientsJson.add("ingredients", ingredientsArray);

        // проверяем ответ создания заказа
        Response responseOrder = order.createOrder(bearerToken, ingredientsJson.toString());
        responseOrder
                .then()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @DisplayName("Создание заказа без авторизации с ингредиентами")
    @Test
    public void checkCreateOrderWithoutAuthAndWithIngredients() {
        bearerToken = "no_token";

        Order order = new Order();
        Ingredients ingredients = new Ingredients().getIngredients();
        List<IngredientData> ingredientsData = ingredients.getData();

        JsonArray ingredientsArray = new JsonArray();
        for (IngredientData ingredient : ingredientsData) {
            ingredientsArray.add(ingredient.get_id());
        }

        JsonObject ingredientsJson = new JsonObject();
        ingredientsJson.add("ingredients", ingredientsArray);

        // проверяем ответ создания заказа
        Response responseOrder = order.createOrder(bearerToken, ingredientsJson.toString());
        responseOrder
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }

    @DisplayName("Создание заказа без авторизации без ингредиентов")
    @Test
    public void checkCreateOrderWithoutAuthAndWithoutIngredients() {
        bearerToken = "no_token";

        Order order = new Order();
        // формируем JSON без ингредиентов
        JsonObject ingredientsJson = new JsonObject();
        ingredientsJson.add("ingredients", new JsonArray());
        // проверяем ответ создания заказа
        Response responseOrder = order.createOrder(bearerToken, ingredientsJson.toString());
        responseOrder
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @DisplayName("Создание заказа без авторизации с невалидным хеш ингредиентов")
    @Test
    public void checkCreateOrderWithoutAuthAndWrongHashIngredients() {
        bearerToken = "no_token";

        Order order = new Order();
        Ingredients ingredients = new Ingredients().getIngredients();
        // получаем объект ингредиентов
        List<IngredientData> ingredientsData = ingredients.getData();

        JsonArray ingredientsArray = new JsonArray();
        for (IngredientData ingredient : ingredientsData) {
            ingredientsArray.add(ingredient.get_id() + "wrongHash");
        }

        JsonObject ingredientsJson = new JsonObject();
        ingredientsJson.add("ingredients", ingredientsArray);

        // проверяем ответ создания заказа
        Response responseOrder = order.createOrder(bearerToken, ingredientsJson.toString());
        responseOrder
                .then()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
