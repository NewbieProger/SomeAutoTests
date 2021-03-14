import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;

/**
 * WileyConfig
 *
 * @blame Android Team
 */
public class WileyConfigAPI {

    public static RequestSpecification wiley_requestSpec;
    public static ResponseSpecification wiley_responseSpec;

    @BeforeClass
    public static void setup() {

        RestAssured.proxy("localhost", 9988);

        wiley_requestSpec = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        wiley_responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

        RestAssured.requestSpecification = wiley_requestSpec;
        RestAssured.responseSpecification = wiley_responseSpec;
    }
}
