package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PetDTO {

  private Category category;
  private Integer id;
  private String name;
  private List<String> photoUrls;
  private String status;
  private List<Tag> tags;

}
