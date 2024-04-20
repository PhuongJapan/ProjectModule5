package ra.project_module5_restapi_240130.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserUpdateDTORequest {
    private String userName;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private boolean status;

}
