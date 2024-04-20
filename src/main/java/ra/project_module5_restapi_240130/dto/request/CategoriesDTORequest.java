package ra.project_module5_restapi_240130.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriesDTORequest {
//    private long id;
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
    private String descriptions;
//    private boolean status;
}
