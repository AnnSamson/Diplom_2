import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Тесты изменения данных пользователя")
public class UpdateUserTest extends BaseTest{
    @DisplayName("Изменение данных пользователя с авторизацией")
    @Test
    public void checkUpdateUserWithAuth() {
        Response response = user.registerUser(userData);
        bearerToken = response.then().extract().path("accessToken");

        userData.setEmail("different_mail_" + userData.getEmail());
        userData.setName("different_name_" + userData.getName());
        userData.setPassword("different_password_" + userData.getPassword());
        Response updateUser = user.updateUser(bearerToken, userData);
        updateUser
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("user.email", equalTo(userData.getEmail()))
                .body("user.name", equalTo(userData.getName()))
                .statusCode(SC_OK);
    }

    @DisplayName("Изменение данных пользователя без авторизации")
    @Test
    public void checkUpdateUserWithoutAuth() {
        Response response = user.registerUser(userData);
        bearerToken = response.then().extract().path("accessToken");

        userData.setEmail("different_mail" + userData.getEmail());
        userData.setName("different_name" + userData.getName());
        userData.setPassword("different_password" + userData.getPassword());

        user.updateUser("no_token", userData)
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .statusCode(SC_UNAUTHORIZED);
    }
}
