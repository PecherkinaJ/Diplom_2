import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestLoginUser {

    private String email;
    private String password;
    private String name;
    private final WebManager webManager = new WebManager();
    private final UserManager userManager = new UserManager();
    private final List<Response> userListToDelete = new ArrayList<>();

    @Before
    public void setUp() {
        RestAssured.baseURI = webManager.getBaseURI();
        email = userManager.getEmail();
        password = userManager.getPassword();
        name = userManager.getName();
    }

    @After
    public void clean() {
        for (Response response : userListToDelete) {
            userManager.deleteUser(response);
        }
        userListToDelete.clear();
    }

    @Test
    public void testLoginUserWithCorrectData() {
        Response response = userManager.createUser(new CreateUser(email, password, name));
        userListToDelete.add(response);
        response = userManager.loginUser(new LoginUser(email, password));
        response.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(email.toLowerCase()))
                .assertThat().body("user.name", equalTo(name))
                .assertThat().body("accessToken", notNullValue())
                .assertThat().body("refreshToken", notNullValue());
    }

    @Test
    public void testLoginUserWithIncorrectPassword() {
        Response response = userManager.createUser(new CreateUser(email, password, name));
        userListToDelete.add(response);
        response = userManager.loginUser(new LoginUser(email, "anotherpassword"));
        response.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void testLoginUserWithIncorrectLogin() {
        Response response = userManager.createUser(new CreateUser(email, password, name));
        userListToDelete.add(response);
        response = userManager.loginUser(new LoginUser("anotheremail", password));
        response.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void testLoginUserWithIncorrectLoginAndPassword() {
        Response response = userManager.createUser(new CreateUser(email, password, name));
        userListToDelete.add(response);
        response = userManager.loginUser(new LoginUser("anotheremail", "anotherpassword"));
        response.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

}
