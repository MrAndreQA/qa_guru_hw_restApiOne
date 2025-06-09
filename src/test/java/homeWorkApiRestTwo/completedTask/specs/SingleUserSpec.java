package homeWorkApiRestTwo.completedTask.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static homeWorkApiRestTwo.completedTask.tests.ApiTestBase.*;
import static homeWorkApiRestTwo.fromLesson.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class SingleUserSpec {

    public static RequestSpecification singleUserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .header("x-api-key", API_KEY)
            .contentType(JSON)
            .baseUri(BASE_URI)
            .basePath(SINGLE_USER_PATH + "{userId}");

    public static ResponseSpecification singleUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification notFoundInSingleUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(STATUS)
            .log(BODY)
            .build();
}