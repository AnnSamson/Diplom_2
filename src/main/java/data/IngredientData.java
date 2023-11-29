package data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientData {
    private String _id;
    private String name;
    private String type;
    private int proteins;
    private int fat;
    private int carbohydrates;
    private int calories;
    private int price;
    private  String image;
    private String image_mobile;
    private String image_large;
    private int __v;

    @Override
    public String toString() {
        return "Data{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", proteins=" + proteins +
                ", fat=" + fat +
                ", carbohydrates=" + carbohydrates +
                ", calories=" + calories +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", image_mobile='" + image_mobile + '\'' +
                ", image_large='" + image_large + '\'' +
                ", __v=" + __v +
                '}';
    }
}
