public class WebManager {

    private String baseURI = "https://stellarburgers.nomoreparties.site";

    private String createUserAPI = "/api/auth/register";
    private String loginUserAPI = "/api/auth/login";
    private String deleteUserAPI = "/api/auth/user";
    private String changeUserAPI = "/api/auth/user";
    private String createOrderAPI = "/api/orders";
    private String getIngredientsAPI = "/api/ingredients";
    private String gerOrdersOfSpecialUserAPI = "/api/orders";

    public String getBaseURI() {
        return baseURI;
    }

    public void setBaseURI(String baseURI) {
        this.baseURI = baseURI;
    }

    public String getCreateUserAPI() {
        return createUserAPI;
    }

    public void setCreateUserAPI(String createUserAPI) {
        this.createUserAPI = createUserAPI;
    }

    public String getLoginUserAPI() {
        return loginUserAPI;
    }

    public void setLoginUserAPI(String loginUserAPI) {
        this.loginUserAPI = loginUserAPI;
    }

    public String getDeleteUserAPI() {
        return deleteUserAPI;
    }

    public void setDeleteUserAPI(String deleteUserAPI) {
        this.deleteUserAPI = deleteUserAPI;
    }

    public String getChangeUserAPI() {
        return changeUserAPI;
    }

    public void setChangeUserAPI(String changeUserAPI) {
        this.changeUserAPI = changeUserAPI;
    }

    public String getCreateOrderAPI() {
        return createOrderAPI;
    }

    public void setCreateOrderAPI(String createOrderAPI) {
        this.createOrderAPI = createOrderAPI;
    }

    public String getGetIngredientsAPI() {
        return getIngredientsAPI;
    }

    public void setGetIngredientsAPI(String getIngredientsAPI) {
        this.getIngredientsAPI = getIngredientsAPI;
    }


    public String getGerOrdersOfSpecialUserAPI() {
        return gerOrdersOfSpecialUserAPI;
    }

    public void setGerOrdersOfSpecialUserAPI(String gerOrdersOfSpecialUserAPI) {
        this.gerOrdersOfSpecialUserAPI = gerOrdersOfSpecialUserAPI;
    }
}