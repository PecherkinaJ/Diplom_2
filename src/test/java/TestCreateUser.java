import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestCreateUser {
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
    public void testCorrectUserCreation() {
        Response response = userManager.createUser(new CreateUser(email, password, name));
        userListToDelete.add(response);
        response.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user", notNullValue())
                .assertThat().body("accessToken", notNullValue());
    }

    @Test
    public void testExistingUserCreation() {
        CreateUser user = new CreateUser(email, password, name);

        Response response = userManager.createUser(user);
        userListToDelete.add(response);
        response.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user", notNullValue())
                .assertThat().body("accessToken", notNullValue());

        userManager.createUser(new CreateUser(email, password, name))
                .then()
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("User already exists"));
    }

    @Test
    public void testIncorrectUserCreationNoEmail() {
        userManager.createUser(new CreateUser("", password, name))
                .then()
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void testIncorrectUserCreationNoPassword() {
        userManager.createUser(new CreateUser(email, "", name))
                .then()
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void testIncorrectUserCreationNoName() {
        userManager.createUser(new CreateUser(email, password, ""))
                .then()
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void testIncorrectUserCreationNoEmailAndPassword() {
        userManager.createUser(new CreateUser("", "", name))
                .then()
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void testIncorrectUserCreationNoEmailAndName() {
        userManager.createUser(new CreateUser("", password, ""))
                .then()
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void testIncorrectUserCreationNoPasswordAndName() {
        userManager.createUser(new CreateUser(email, "", ""))
                .then()
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}
