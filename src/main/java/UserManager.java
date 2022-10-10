import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class UserManager {
    private final WebManager webManager = new WebManager();
    private final String[] email = {"bestMail_1@yandex.ru", "worstMail_1@yandex.ru", "justAMail_1@yandex.ru", "noMail_1@yandex.ru", "MailMail_1@yandex.ru", "funnyMail_1@yandex.ru", "sadMail_1@yandex.ru"};
    private final String[] password = {"bestPass", "Password", "pAssworD", "YouShallNotPass", "boringpassword", "123456798", "qwerty"};
    private final String[] name = {"NoName", "HaveName", "JustAName", "Nameless", "BestName", "Sam", "Dean"};

    @Step("Get one of the email")
    public String getEmail() {
        return email[new Random().nextInt(email.length)];
    }

    @Step("Get one of the passwords")
    public String getPassword() {
        return password[new Random().nextInt(password.length)];
    }

    @Step("Get one of the names")
    public String getName() {
        return name[new Random().nextInt(name.length)];
    }

    @Step("Create user")
    public Response createUser(CreateUser user) {
        String createAPI = webManager.getCreateUserAPI();
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(createAPI);
    }

    @Step("Login user")
    public Response loginUser(LoginUser user) {
        String loginAPI = webManager.getLoginUserAPI();
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(loginAPI);
    }

    @Step("Delete user")
    public void deleteUser(Response resp) {
        String deleteAPI = webManager.getDeleteUserAPI();
        String tempToken = resp.then().extract().path("accessToken");
        String token = tempToken.replace("Bearer ", "");

        given()
                .auth().oauth2(token)
                .delete(deleteAPI);
    }

    @Step("Change user with auth")
    public Response changeUserWithAuth(Response resp, CreateUser newUserData) {
        String changeAPI = webManager.getChangeUserAPI();

        String tempToken = resp.then().extract().path("accessToken");
        String token = tempToken.replace("Bearer ", "");

        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .body(newUserData)
                .when()
                .patch(changeAPI);
    }

    @Step("Change user without auth")
    public Response changeUserWithoutAuth(CreateUser newUserData) {
        String changeAPI = webManager.getChangeUserAPI();

        return given()
                .header("Content-type", "application/json")
                .body(newUserData)
                .when()
                .patch(changeAPI);
    }

}
