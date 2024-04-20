package ra.project_module5_restapi_240130.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserChangePasswordRequest {
    private String oldPass;
    private String newPass;
    private String confirmNewPass;

}
