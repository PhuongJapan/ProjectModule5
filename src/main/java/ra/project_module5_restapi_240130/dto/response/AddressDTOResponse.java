package ra.project_module5_restapi_240130.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDTOResponse {
    private long addressId;
    private String fullAddress;
    private String phone;
    private String receiveName;
    private long userId;
    private String userName;
}
