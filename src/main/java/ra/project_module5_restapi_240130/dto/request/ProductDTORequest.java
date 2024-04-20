package ra.project_module5_restapi_240130.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTORequest {
    @NotBlank(message = "Tên danh mục không được để trống")
    @Column(nullable = false, unique = true)
    private String name;
    private String descriptions;
    private double unit_price;
    private String image;
    @Min(value = 1,message = "Giá trị hàng tồn nhỏ nhất là 1")
    private int stock_quantity;
    private long catalogId;
}
