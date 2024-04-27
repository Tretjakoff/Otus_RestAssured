package pet.addnewpet;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dto.Category;
import dto.PetDTO;
import dto.Tag;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import services.PetServiceApi;

import java.util.Arrays;
import java.util.List;

public class AddNewPetTest {

  /*
  В тесте проверяю статус-код и валидацию по json-схеме ответа POST-запроса
  'https://petstore.swagger.io/v2/pet' - добавление нового питомца в магазин
   */
  @Test
  public void checkJsonSchemaAddNewPet() {

    PetServiceApi petServiceApi = new PetServiceApi();

    PetDTO petDTO = PetDTO.builder()
        .category(Category.builder()
            .id(2)
            .name("dog")
            .build())
        .id(1)
        .name("Sharik")
        .photoUrls(Arrays.asList("http://photo1", "http://photo2"))
        .tags(Arrays.asList(Tag.builder()
                .id(1)
                .name("friend")
                .build(),
            Tag.builder()
                .id(2)
                .name("kindly")
                .build()))
        .status("available")
        .build();

    petServiceApi.addNewPetToTheStore(petDTO)
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schema/NewPet.json"));
  }

  /*
  В тесте проверяю все значения JSON ответа POST-запроса
  'https://petstore.swagger.io/v2/pet' - добавление нового питомца в магазин
   */
  @Test
  public void checkJsonFieldsAddNewPet() {

    Integer idPet = 1;
    Integer idCategory = 2;
    String nameCategory = "dog";
    String namePet = "Sharik";
    List<String> photoUrls = Arrays.asList("http://photo1", "http://photo2");
    Integer idTag = 2;
    String nameTag = "friend";
    List<Tag> listTag = List.of(Tag.builder()
        .id(idTag)
        .name(nameTag)
        .build());
    String status = "available";

    PetServiceApi petServiceApi = new PetServiceApi();

    PetDTO petDTO = PetDTO.builder()
        .category(Category.builder()
            .id(idCategory)
            .name(nameCategory)
            .build())
        .id(idPet)
        .name(namePet)
        .photoUrls(photoUrls)
        .tags(listTag)
        .status(status)
        .build();

    JsonPath response = petServiceApi.addNewPetToTheStore(petDTO)
        .statusCode(200)
        .extract().jsonPath();

    assertEquals(idPet, response.getInt("id"), "id pet is incorrect");
    assertEquals(idCategory, response.getInt("category.id"), "id category pet is incorrect");
    assertEquals(nameCategory, response.get("category.name"), "name category pet is incorrect");
    assertEquals(namePet, response.get("name"), "name pet is incorrect");
    assertEquals(photoUrls, response.getList("photoUrls"), "photoUrls pet is incorrect");
    assertEquals(idTag, response.getList("tags.id").get(0), "id tags pet is incorrect");
    assertEquals(nameTag, response.getList("tags.name").get(0), "name tags pet is incorrect");
    assertEquals(status, response.get("status"), "status pet is incorrect");
  }
}
