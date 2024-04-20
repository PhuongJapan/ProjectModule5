package ra.project_module5_restapi_240130.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import ra.project_module5_restapi_240130.model.Product;

import java.util.List;

@AllArgsConstructor
@Data
public class CategoriesDTOResponse {
    private long id;
    private String name;
    private String descriptions;
    private boolean status;
    @JsonIgnoreProperties({"catalog"})
    private List<Product> listProduct;
}
