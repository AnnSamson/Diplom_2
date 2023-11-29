import data.UserData;
import io.restassured.RestAssured;
import methods.User;
import org.junit.After;
import org.junit.Before;

public class BaseTest {
    User user;
    UserData userData;
    String bearerToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        userData = new UserData();
        user = new User();
    }

    @After
    public void deleteUser() {
        user.deleteUser(bearerToken);
    }
}
