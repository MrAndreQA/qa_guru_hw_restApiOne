package homeWorkApiRestOne;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class ApiTestBase {

    public String registerEndpoint = "register";
    public String apiKey = "reqres-free-v1";
    public String usersEndpoint = "users/";

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in/api/"; }
}
