package services;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import dto.PetDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PetServiceApi {
  private static final String BASE_URL = System.getProperty("baseUri");
  private static final String BASE_PATH = "/pet";
  private RequestSpecification spec;

  public PetServiceApi() {
    spec = given()
        .baseUri(BASE_URL)
        .contentType(ContentType.JSON);
  }

  public ValidatableResponse addNewPetToTheStore(PetDTO petDTO) {

    return given(spec)
        .basePath(BASE_PATH)
        .body(petDTO)
        .log().all()
        .when()
        .post()
        .then()
        .log().all()
        .body(matchesJsonSchemaInClasspath("schema/NewPet.json"));
  }

  public ValidatableResponse findPetByID(Object id) {

    return given(spec)
        .basePath(BASE_PATH)
        .pathParam("petId", id)
        .log().all()
        .when()
        .get("/{petId}")
        .then()
        .log().all();
  }

  public ValidatableResponse deletePetByID(Object id) {

    return given(spec)
        .basePath(BASE_PATH)
        .pathParam("petId", id)
        .log().all()
        .when()
        .delete("/{petId}")
        .then()
        .log().all();
  }
}
