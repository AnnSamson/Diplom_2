package data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static io.restassured.RestAssured.given;

@Getter
@Setter
public class Ingredients {
    private boolean success;
    private List<IngredientData> data;

    @Override
    public String toString() {
        return "Ingredients{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
    public Ingredients getIngredients() {
        return given()
                .header("Content-type", "application/json")
                .get(ApiUrl.GET_INGREDIENTS)
                .body().as(Ingredients.class);
    }
}
