package pet.findpet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.javafaker.Faker;
import dto.Category;
import dto.PetDTO;
import dto.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import services.PetServiceApi;

import java.util.Arrays;
import java.util.List;

public class PositiveFindPetByIDTest {

  private final Faker faker = new Faker();
  private Long idPet;

  @AfterEach
  public void deletePet() {
    PetServiceApi petServiceApi = new PetServiceApi();
    petServiceApi.deletePetByID(idPet)
        .statusCode(200);
  }

  /*
  В тесте проверяю что при GET-запросе с корректным id возвращается статус-код - 200
  и объект с верными данными
  'https://petstore.swagger.io/v2/pet/{petId}' (поиск питомца по id)
   */
  @Test
  public void checkCorrectStatusCodeFindPetByID() {

    idPet = 15L;

    PetServiceApi petServiceApi = new PetServiceApi();

    PetDTO petDTO = PetDTO.builder()
        .category(Category.builder()
            .id(3)
            .name("dog")
            .build())
        .id(idPet)
        .name(faker.dog().name())
        .photoUrls(Arrays.asList("http://photo1", "http://photo2"))
        .tags(List.of(Tag.builder()
            .id(3)
            .name("friend")
            .build()))
        .status("available")
        .build();

    petServiceApi.addNewPetToTheStore(petDTO)
        .statusCode(200);

    PetDTO actualPet = petServiceApi.findPetByID(idPet)
        .statusCode(200)
        .extract().as(PetDTO.class);

    assertEquals(petDTO, actualPet, "response object is incorrect");
  }
}
