package ra.project_module5_restapi_240130.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignInResponse {
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private String address;
    private final String TYPE = "Bearer";
    //Kiểu token
    private Collection<? extends GrantedAuthority> authorities;
    //Trả về quyền
    private String accessToken;
    private String refreshToken;

}
