package ra.project_module5_restapi_240130.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpRequest {
    @NotBlank(message = "Tên tài khoản không được để trống")
    @Length(min = 6, max = 100, message = "Độ dài tên tài khoản từ 6-100 ký tự")
    private String userName;
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Không đúng định dạng email")
    private String email;
    @NotNull(message = "Tên người dùng không được để trống")
    private String fullName;
    @Pattern(regexp = "^(\\+?84|0)\\d{9,10}$", message = "Không đúng định dạng số điện thoại Việt Nam")
    private String phone;
    @NotNull(message = "Địa chỉ không được để trống")
    private String address;
    private List<String> listRole;
}
