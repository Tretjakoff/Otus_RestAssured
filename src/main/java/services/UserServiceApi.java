package services;

import dto.UserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserServiceApi {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String BASE_PATH = "/user";
    private RequestSpecification spec;

    public UserServiceApi(){
        spec = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
    }

    public ValidatableResponse createNewUser(UserDTO user) {

        return given(spec)
                .basePath(BASE_PATH)
                .body(user)
                .log().all()
                .when()
                .post()
                .then()
                .log().all();
    }
}
