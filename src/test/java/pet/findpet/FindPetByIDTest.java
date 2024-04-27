package pet.findpet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dto.Category;
import dto.PetDTO;
import dto.PetResponseDTO;
import dto.Tag;
import org.junit.jupiter.api.Test;
import services.PetServiceApi;

import java.util.Arrays;
import java.util.List;

public class FindPetByIDTest {

  /*
  В тесте проверяю что при GET-запросе 'https://petstore.swagger.io/v2/pet/{petId}' (поиск питомца по id)
  с корректным id статус код - 200
   */
  @Test
  public void checkCorrectStatusCodeFindPetByID() {

    Integer idPet = 15;

    PetServiceApi petServiceApi = new PetServiceApi();

    PetDTO petDTO = PetDTO.builder()
        .category(Category.builder()
            .id(3)
            .name("dog")
            .build())
        .id(idPet)
        .name("Bobik")
        .photoUrls(Arrays.asList("http://photo1", "http://photo2"))
        .tags(List.of(Tag.builder()
            .id(3)
            .name("friend")
            .build()))
        .status("available")
        .build();

    petServiceApi.addNewPetToTheStore(petDTO)
        .statusCode(200);

    petServiceApi.findPetByID(idPet)
        .statusCode(200);
  }

  /*
  В тесте проверяю что при GET-запросе 'https://petstore.swagger.io/v2/pet/{petId}' (поиск питомца по id)
  с id отсутствующего питомца статус код - 404 и в теле ответа приходит верное сообщение об ошибке
   */
  @Test
  public void checkIncorrectStatusCodeFindPetByID() {

    Long idPet = 15555555555555L;

    PetServiceApi petServiceApi = new PetServiceApi();

    PetResponseDTO response = petServiceApi.findPetByID(idPet)
        .statusCode(404)
        .extract().body().as(PetResponseDTO.class);

    assertEquals(1L, response.getCode(), "code is incorrect");
    assertEquals("error", response.getType(), "type is incorrect");
    assertEquals("Pet not found", response.getMessage(), "message is incorrect");
  }
}
