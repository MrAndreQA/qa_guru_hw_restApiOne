package tests;

import com.codeborne.selenide.Configuration;
import configs.ApiConfig;
import configs.ApiProvider;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.Map;
import java.util.UUID;

public class ApiTestBase {

    public static final ApiConfig apiConfig = ApiProvider.getApiConfig();

    protected static final String API_KEY_PARAM = apiConfig.apiKeyParam();
    protected static final String API_KEY_VALUE = apiConfig.apiKeyValue();
    public static final String REGISTER_ENDPOINT = apiConfig.registerEndpoint();
    public static final String USERS_ENDPOINT = apiConfig.usersEndpoint();
    protected static final String USER_EMAIL = apiConfig.userEmail();
    protected static final String USER_PASSWORD = apiConfig.userPassword();


    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = ApiProvider.getApiConfig().baseApiUri();
        RestAssured.basePath = ApiProvider.getApiConfig().basePath();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true,
                "name", "Test: " + UUID.randomUUID()
        ));
        Configuration.browserCapabilities = capabilities;
    }
}