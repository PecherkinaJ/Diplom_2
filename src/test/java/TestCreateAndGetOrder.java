import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class TestCreateAndGetOrder {
    private final List<Response> userListToDelete = new ArrayList<>();

    private final WebManager webManager = new WebManager();
    private final UserManager userManager = new UserManager();
    private final OrderManager orderManager = new OrderManager();
    Response resp;

    @Before
    public void setUp() {
        RestAssured.baseURI = webManager.getBaseURI();
    }

    @After
    public void clean() {
        for (Response response : userListToDelete) {
            userManager.deleteUser(response);
        }
        userListToDelete.clear();
    }

    private void createNewUser() {
        String email = userManager.getEmail();
        String password = userManager.getPassword();
        String name = userManager.getName();

        resp = userManager.createUser(new CreateUser(email, password, name));
        userListToDelete.add(resp);
    }

    @Test
    public void testCreateOrderWithAuthAndIngredients() {
        createNewUser();
        ArrayList<String> ingredientList = new ArrayList<>();
        ingredientList.add(orderManager.getRandomIngredient());
        ingredientList.add(orderManager.getRandomIngredient());

        Ingredients ingredients = new Ingredients(ingredientList);
        Response response = orderManager.createOrderWithAuth(ingredients, resp);
        response.then()
                .statusCode(200)
                .assertThat().body("name", notNullValue())
                .assertThat().body("order", notNullValue())
                .assertThat().body("order.number", notNullValue())
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void testCreateOrderWithAuthAndWithoutIngredients() {
        createNewUser();
        Response response = orderManager.createOrderWithAuth(resp);
        response.then()
                .statusCode(400)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void testCreateOrderWithoutAuthAndWithoutIngredients() {
        Response response = orderManager.createOrderWithoutAuth();
        response.then()
                .statusCode(400)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void testCreateOrderWithoutAuthAndWithIngredients() {
        ArrayList<String> ingredientList = new ArrayList<>();
        ingredientList.add(orderManager.getRandomIngredient());
        ingredientList.add(orderManager.getRandomIngredient());

        Ingredients ingredients = new Ingredients(ingredientList);
        Response response = orderManager.createOrderWithoutAuth(ingredients);
        response.then()
                .statusCode(200)
                .assertThat().body("name", notNullValue())
                .assertThat().body("order", notNullValue())
                .assertThat().body("order.number", notNullValue())
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void testCreateOrderWrongIngredientsWithAuth() {
        createNewUser();
        ArrayList<String> ingredientList = new ArrayList<>();
        ingredientList.add("0021665");
        ingredientList.add("0594665");

        Ingredients ingredients = new Ingredients(ingredientList);
        Response response = orderManager.createOrderWithAuth(ingredients, resp);
        response.then()
                .assertThat().statusCode(500);
    }

    @Test
    public void testCreateOrderWrongIngredientsWithoutAuth() {
        ArrayList<String> ingredientList = new ArrayList<>();
        ingredientList.add("0021665");
        ingredientList.add("0594665");

        Ingredients ingredients = new Ingredients(ingredientList);
        Response response = orderManager.createOrderWithoutAuth(ingredients);
        response.then()
                .assertThat().statusCode(500);
    }

    @Test
    public void testGetOrderOfSpecialUserWithoutAuth() {
        Response response = orderManager.getOrderWithoutAuth();
        response.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void testGetOrderOfSpecialUserWithAuthSomeOrders() {
        createNewUser();

        ArrayList<String> ingredientList1 = new ArrayList<>();
        ingredientList1.add(orderManager.getRandomIngredient());
        ingredientList1.add(orderManager.getRandomIngredient());
        Ingredients ingredients = new Ingredients(ingredientList1);
        orderManager.createOrderWithAuth(ingredients, resp);

        ArrayList<String> ingredientList2 = new ArrayList<>();
        ingredientList2.add(orderManager.getRandomIngredient());
        ingredientList2.add(orderManager.getRandomIngredient());
        ingredients = new Ingredients(ingredientList2);
        orderManager.createOrderWithAuth(ingredients, resp);

        Response response = orderManager.getOrderWithAuth(resp);
        response.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("orders", notNullValue())
                .assertThat().body("orders.ingredients[0]", notNullValue())
                .assertThat().body("orders.ingredients[1]", notNullValue())
                .assertThat().body("total", Matchers.instanceOf(Integer.class))
                .assertThat().body("totalToday", Matchers.instanceOf(Integer.class));
    }

    @Test
    public void testGetOrderOfSpecialUserWithAuthOneOrder() {
        createNewUser();

        ArrayList<String> ingredientList = new ArrayList<>();
        ingredientList.add(orderManager.getRandomIngredient());
        ingredientList.add(orderManager.getRandomIngredient());
        Ingredients ingredients = new Ingredients(ingredientList);
        orderManager.createOrderWithAuth(ingredients, resp);

        Response response = orderManager.getOrderWithAuth(resp);
        response.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("orders", notNullValue())
                .assertThat().body("orders.ingredients", notNullValue())
                .assertThat().body("total", Matchers.instanceOf(Integer.class))
                .assertThat().body("totalToday", Matchers.instanceOf(Integer.class));
    }

    @Test
    public void testGetOrderOfSpecialUserWithAuthZeroOrders() {
        createNewUser();

        Response response = orderManager.getOrderWithAuth(resp);
        response.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("orders", empty())
                .assertThat().body("total", Matchers.instanceOf(Integer.class))
                .assertThat().body("totalToday", Matchers.instanceOf(Integer.class));
    }

}
