package pet.findpet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.javafaker.Faker;
import dto.PetResponseDTO;
import org.junit.jupiter.api.Test;
import services.PetServiceApi;

public class NegativeFindPetByIDTest {

  private final Faker faker = new Faker();

  /*
   В тесте проверяю что при GET-запросе с id отсутствующего питомца
   возвращается статус-код - 404 и в теле ответа приходит верное сообщение об ошибке
  'https://petstore.swagger.io/v2/pet/{petId}' (поиск питомца по id)
   */
  @Test
  public void checkIncorrectStatusCodeFindPetByID() {

    Long idPet = faker.number().numberBetween(1000000000000L, 10000000000000L);

    PetServiceApi petServiceApi = new PetServiceApi();

    PetResponseDTO response = petServiceApi.findPetByID(idPet)
        .statusCode(404)
        .extract().body().as(PetResponseDTO.class);

    assertEquals(1L, response.getCode(), "code is incorrect");
    assertEquals("error", response.getType(), "type is incorrect");
    assertEquals("Pet not found", response.getMessage(), "message is incorrect");
  }
}
