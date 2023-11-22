import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.json.JSONObject;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты авторизации пользователя")
public class LoginUserTest extends BaseTest{
    @DisplayName("Авторизация существующего пользователя")
    @Test
    public void checkLoginUser() {
        user.registerUser(userData);
        JSONObject json = new JSONObject();
        json.put("email", userData.getEmail());
        json.put("password", userData.getPassword());

        Response response = user.loginUser(json.toString());
        response
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("user.email", equalTo(userData.getEmail()))
                .body("user.name", equalTo(userData.getName()))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .statusCode(SC_OK);

        bearerToken = response.then().extract().path("accessToken");
    }

    @DisplayName("Авторизация с неверным логином")
    @Test
    public void checkLoginUserWithWrongLogin() {
        Response response = user.registerUser(userData);
        bearerToken = response.then().extract().path("accessToken");
        JSONObject json = new JSONObject();
        json.put("email", "false" + userData.getEmail());
        json.put("password", userData.getPassword());

        user.loginUser(json.toString())
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(SC_UNAUTHORIZED);

    }

    @DisplayName("Авторизация с неверным паролем")
    @Test
    public void checkLoginUserWithWrongPassword() {
        Response response = user.registerUser(userData);
        bearerToken = response.then().extract().path("accessToken");

        JSONObject json = new JSONObject();
        json.put("email", userData.getEmail());
        json.put("password", "false" + userData.getPassword());

        user.loginUser(json.toString())
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(SC_UNAUTHORIZED);
    }
}
