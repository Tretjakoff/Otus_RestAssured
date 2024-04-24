package User.сreateUser;

import dto.UserDTO;
import dto.UserResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.UserServiceApi;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CreateNewUserTest {


    @Test
    public void createNewUser(){

        UserServiceApi userApi = new UserServiceApi();

        UserDTO userDTO = UserDTO.builder()
                .id(100l)
                .email("email@mail.ru")
                .password("12123")
                .userStatus(408l)
                .lastName("lastName")
                .build();

      userApi.createNewUser(userDTO)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schema/CreateUser.json"))
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", equalTo("100"));

       /*String actualCode = userApi.createNewUser(userDTO).extract().body().jsonPath().get("code").toString();
       String expectedCode = "405";

       Assertions.assertEquals(expectedCode, actualCode, "Code is incorrect");*/


        UserResponseDTO actualUser = userApi.createNewUser(userDTO).extract().body().as(UserResponseDTO.class);


        Assertions.assertEquals("unknown", actualUser.getType());
        Assertions.assertEquals(200, actualUser.getCode());
        Assertions.assertEquals("100", actualUser.getMessage());

    }
}
