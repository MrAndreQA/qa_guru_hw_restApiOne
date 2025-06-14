package homeWorkApiRestTwo.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class ApiTestBase {

    public static final String API_KEY = "reqres-free-v1";
    public static String REGISTER_ENDPOINT = "/register";
    public static String USERS_ENDPOINT = "/users/";

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
}