package ra.project_module5_restapi_240130.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ra.project_module5_restapi_240130.model.Roles;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private long id;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private String address;
    private boolean status;
    private String phone;
    private List<String> listRoles;
}
