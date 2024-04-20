package ra.project_module5_restapi_240130.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressDTORequest {
    private String fullAddress;
    private String phone;
    private String receiveName;
    private long userId;
}
