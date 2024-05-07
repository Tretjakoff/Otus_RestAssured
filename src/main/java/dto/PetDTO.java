package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {

  private Category category;
  private Long id;
  private String name;
  private List<String> photoUrls;
  private String status;
  private List<Tag> tags;

}
