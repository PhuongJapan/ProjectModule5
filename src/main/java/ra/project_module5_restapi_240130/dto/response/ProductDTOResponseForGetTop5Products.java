package ra.project_module5_restapi_240130.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class ProductDTOResponseForGetTop5Products {
    private long id;
    private String name;
    private String descriptions;
    private double unit_price;
    private String image;
    private int stock_quantity;
    private long catalogId;
    private String catalogName;
    private Date createdAt;
    private boolean status;
}
