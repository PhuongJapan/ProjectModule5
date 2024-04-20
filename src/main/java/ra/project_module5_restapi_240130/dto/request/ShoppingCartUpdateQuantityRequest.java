package ra.project_module5_restapi_240130.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCartUpdateQuantityRequest {
    @Min(value = 1,message = "Số lượng nhỏ nhất là 1")
    private int quantity;

}
