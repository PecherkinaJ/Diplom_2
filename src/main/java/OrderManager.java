import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class OrderManager {

    private final WebManager webManager = new WebManager();
    private final String ingredientsAPI = webManager.getGetIngredientsAPI();
    private final String createOrderAPI = webManager.getCreateOrderAPI();
    private final String getSpecialUserOrdersAPI = webManager.getGerOrdersOfSpecialUserAPI();

    List<String> ingredients;

    @Step("Get all ingredients")
    public void getAllIngredients() {
        ingredients = given()
                .header("Content-type", "application/json")
                .get(ingredientsAPI)
                .then()
                .extract().body().path("data._id");
    }

    @Step("Get random ingredient")
    public String getRandomIngredient() {
        getAllIngredients();
        return ingredients.get(new Random().nextInt(ingredients.size()));
    }

    @Step("Creation order with Auth with ingredients")
    public Response createOrderWithAuth(Ingredients ingredients, Response response) {
        String tempToken = response.then().extract().path("accessToken");
        String token = tempToken.replace("Bearer ", "");
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .body(ingredients)
                .when()
                .post(createOrderAPI);
    }

    @Step("Creation order with Auth without ingredients")
    public Response createOrderWithAuth(Response response) {
        String tempToken = response.then().extract().path("accessToken");
        String token = tempToken.replace("Bearer ", "");

        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .when()
                .post(createOrderAPI);
    }

    @Step("Creation order without Auth and without ingredients")
    public Response createOrderWithoutAuth() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .post(createOrderAPI);
    }

    @Step("Creation order without Auth and with ingredients")
    public Response createOrderWithoutAuth(Ingredients ingredients) {
        return given()
                .header("Content-type", "application/json")
                .body(ingredients)
                .when()
                .post(createOrderAPI);
    }


    @Step("Get orders of the specific user with auth")
    public Response getOrderWithAuth(Response response) {
        String tempToken = response.then().extract().path("accessToken");
        String token = tempToken.replace("Bearer ", "");

        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .when()
                .get(getSpecialUserOrdersAPI);
    }

    @Step("Get orders of the specific user without auth")
    public Response getOrderWithoutAuth() {

        return given()
                .header("Content-type", "application/json")
                .when()
                .get(getSpecialUserOrdersAPI);
    }
}
