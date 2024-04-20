package ra.project_module5_restapi_240130.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.project_module5_restapi_240130.model.Product;
import ra.project_module5_restapi_240130.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCartDTORequest {
    @Min(value = 1,message = "Số lượng nhỏ nhất là 1")
    private int quantity;
    private long userId;
    private long productId;
}
