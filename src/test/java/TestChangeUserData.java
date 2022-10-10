import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class TestChangeUserData {

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
    public void testChangeUserDataWithAuthNewEmail() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newEmail = "newestemail@eeeemail.ru";
        user.setEmail(newEmail);
        Response resp = userManager.changeUserWithAuth(response, user);
        resp.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(newEmail.toLowerCase()))
                .assertThat().body("user.name", equalTo(name));
    }

    @Test
    public void testChangeUserDataWithAuthNewPassword() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newPass = "newestpass";
        user.setPassword(newPass);
        Response resp = userManager.changeUserWithAuth(response, user);
        resp.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(email.toLowerCase()))
                .assertThat().body("user.name", equalTo(name));
    }

    @Test
    public void testChangeUserDataWithAuthNewName() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newName = "newestName";
        user.setName(newName);
        Response resp = userManager.changeUserWithAuth(response, user);
        resp.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(email.toLowerCase()))
                .assertThat().body("user.name", equalTo(newName));
    }

    @Test
    public void testChangeUserDataWithAuthNewEmailAndName() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newEmail = "newestEmail@yandex.ru";
        String newName = "newestName";
        user.setEmail(newEmail);
        user.setName(newName);
        Response resp = userManager.changeUserWithAuth(response, user);
        resp.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(newEmail.toLowerCase()))
                .assertThat().body("user.name", equalTo(newName));
    }

    @Test
    public void testChangeUserDataWithAuthNewEmailAndPassword() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newEmail = "newestEmail@yandex.ru";
        String newPass = "newestPass";
        user.setEmail(newEmail);
        user.setPassword(newPass);
        Response resp = userManager.changeUserWithAuth(response, user);
        resp.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(newEmail.toLowerCase()))
                .assertThat().body("user.name", equalTo(name));
    }

    @Test
    public void testChangeUserDataWithAuthNewNameAndPassword() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newName = "newestName";
        String newPass = "newestPass";
        user.setName(newName);
        user.setPassword(newPass);
        Response resp = userManager.changeUserWithAuth(response, user);
        resp.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(email.toLowerCase()))
                .assertThat().body("user.name", equalTo(newName));
    }

    @Test
    public void testChangeUserDataWithAuthNewEmailAndNameAndPassword() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newEmail = "newestEmail@yandex.ru";
        String newPass = "newestPass";
        String newName = "newestName";
        user.setEmail(newEmail);
        user.setName(newName);
        user.setPassword(newPass);
        Response resp = userManager.changeUserWithAuth(response, user);
        resp.then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(newEmail.toLowerCase()))
                .assertThat().body("user.name", equalTo(newName));
    }

    @Test
    public void testChangeUserDataWithoutAuthNewEmail() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newEmail = "newestemail@eeeemail.ru";
        user.setEmail(newEmail);
        Response resp = userManager.changeUserWithoutAuth(user);
        resp.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void testChangeUserDataWithoutAuthNewPassword() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newPass = "newestpass";
        user.setPassword(newPass);
        Response resp = userManager.changeUserWithoutAuth(user);
        resp.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void testChangeUserDataWithoutAuthNewName() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newName = "newestName";
        user.setName(newName);
        Response resp = userManager.changeUserWithoutAuth(user);
        resp.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void testChangeUserDataWithoutAuthNewEmailAndName() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newEmail = "newestEmail@yandex.ru";
        String newName = "newestName";
        user.setEmail(newEmail);
        user.setName(newName);
        Response resp = userManager.changeUserWithoutAuth(user);
        resp.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void testChangeUserDataWithoutAuthNewEmailAndPassword() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newEmail = "newestEmail@yandex.ru";
        String newPass = "newestPass";
        user.setEmail(newEmail);
        user.setPassword(newPass);
        Response resp = userManager.changeUserWithoutAuth(user);
        resp.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void testChangeUserDataWithoutAuthNewNameAndPassword() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newName = "newestName";
        String newPass = "newestPass";
        user.setName(newName);
        user.setPassword(newPass);
        Response resp = userManager.changeUserWithoutAuth(user);
        resp.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void testChangeUserDataWithoutAuthNewEmailAndNameAndPassword() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String newEmail = "newestEmail@yandex.ru";
        String newPass = "newestPass";
        String newName = "newestName";
        user.setEmail(newEmail);
        user.setName(newName);
        user.setPassword(newPass);
        Response resp = userManager.changeUserWithoutAuth(user);
        resp.then()
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void testChangeUserDataWithAuthNewNameAndPasswordAndExistingEmail() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String emailTemp = "notrepitableuseremail@yandex.ru";
        String passwordTemp = userManager.getPassword();
        String nameTemp = userManager.getName();
        CreateUser userTemp = new CreateUser(emailTemp, passwordTemp, nameTemp);
        Response responseTemp = userManager.createUser(userTemp);
        userListToDelete.add(responseTemp);

        user.setEmail(emailTemp);
        Response resp = userManager.changeUserWithAuth(response, user);
        resp.then()
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("User with such email already exists"));
    }

    @Test
    public void testChangeUserDataWithAuthNewPasswordAndExistingEmailAndName() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String emailTemp = "notrepitableuseremail@yandex.ru";
        String passwordTemp = userManager.getPassword();
        String nameTemp = userManager.getName();
        CreateUser userTemp = new CreateUser(emailTemp, passwordTemp, nameTemp);
        Response responseTemp = userManager.createUser(userTemp);
        userListToDelete.add(responseTemp);

        user.setEmail(emailTemp);
        user.setName(nameTemp);
        Response resp = userManager.changeUserWithAuth(response, user);
        resp.then()
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("User with such email already exists"));
    }

    @Test
    public void testChangeUserDataWithAuthExistingEmailAndPasswordAndName() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String emailTemp = "notrepitableuseremail@yandex.ru";
        String passwordTemp = userManager.getPassword();
        String nameTemp = userManager.getName();
        CreateUser userTemp = new CreateUser(emailTemp, passwordTemp, nameTemp);
        Response responseTemp = userManager.createUser(userTemp);
        userListToDelete.add(responseTemp);

        user.setEmail(emailTemp);
        user.setPassword(passwordTemp);
        user.setName(nameTemp);
        Response resp = userManager.changeUserWithAuth(response, user);
        resp.then()
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("User with such email already exists"));
    }

    @Test
    public void testChangeUserDataWithAuthNewEmailAndPasswordAndExistingName() {
        CreateUser user = new CreateUser(email, password, name);
        Response response = userManager.createUser(user);
        userListToDelete.add(response);

        String nameTemp = "newusername";
        Response responseTemp = userManager.createUser(new CreateUser("notrepitableuseremail@yandex.ru", password, nameTemp));
        userListToDelete.add(responseTemp);

        user.setName(nameTemp);
        userManager.changeUserWithAuth(response, user)
                .then()
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(email.toLowerCase()))
                .assertThat().body("user.name", equalTo(nameTemp));
    }

}
