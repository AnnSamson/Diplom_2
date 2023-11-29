import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты создания нового пользователя")
public class CreateUserTest extends BaseTest {
    @DisplayName("Создание уникального пользователя")
    @Test
    public void checkCreateNewUser() {
        Response response = user.registerUser(userData);
        response
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(userData.getEmail()))
                .body("user.name", equalTo(userData.getName()))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
        bearerToken = response.then().extract().path("accessToken");
    }

    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Test
    public void checkDoubleCreateNewUser() {
        Response response = user.registerUser(userData);
        bearerToken = response.then().extract().path("accessToken");
        user.registerUser(userData)
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @DisplayName("Создание уникального пользователя без email")
    @Test
    public void checkCreateNewUserWithoutLogin() {
        userData.setEmail("");
        Response response = user.registerUser(userData);
        response
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
        bearerToken = response.then().extract().path("accessToken");
    }

    @DisplayName("Создание уникального пользователя без password")
    @Test
    public void checkCreateNewUserWithoutPassword() {
        userData.setPassword("");
        Response response = user.registerUser(userData);
        response
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
        bearerToken = response.then().extract().path("accessToken");
    }

    @DisplayName("Создание уникального пользователя без name")
    @Test
    public void checkCreateNewUserWithoutName() {
        userData.setName("");
        Response response = user.registerUser(userData);
        response
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
        bearerToken = response.then().extract().path("accessToken");
    }

    @DisplayName("Проверка bearerToken")
    @After
    public void bearerTokenCheck() {
        if (bearerToken == null) {
            bearerToken = "noting";
        }
    }
}
