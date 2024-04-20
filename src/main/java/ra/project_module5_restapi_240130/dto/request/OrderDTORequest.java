package ra.project_module5_restapi_240130.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.project_module5_restapi_240130.model.EOrder;
import ra.project_module5_restapi_240130.model.ShoppingCart;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTORequest {
    private List<Long> listShopCartId;
    private String note;
    private long userId;
    private long addressId;
    private EOrder status;
}

