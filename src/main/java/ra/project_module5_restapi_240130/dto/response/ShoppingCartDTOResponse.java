package ra.project_module5_restapi_240130.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ShoppingCartDTOResponse {
    private long id;
    private long userId;
    private String userFullName;
    private long productId;
    private String productName;
    private int order_quantity;

}
