import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqresTest {
    public static final String urlGet = "https://reqres.in/api/users/2";

    Response response;

    @Test(description = "Статус кода 200 и FirstName")
    public void testStatusAndFirstName() {
        response = given()
                .get(urlGet)
                    .then()
                .statusCode(200)
                .body("data.first_name", equalTo("Janet"))
                .extract().response();

    }

    @Test(description = "")
    public void testQwe() {
        given()
                .baseUri("https://master.is-mis.ru/")
                .basePath("/pats/patients/8269839/edit")
                .auth().basic("admin", "adminadmin")
//                .header("Authorization", "Basic YWRtaW46SWs0SEwwdlQ=")
                    .when()
                .get().getBody().prettyPrint();

    }
}
