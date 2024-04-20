package ra.project_module5_restapi_240130.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDTORequest {
    @NotNull(message = "Tên người dùng không được để trống")
    @Length(min = 6, max = 100, message = "Độ dài tên tài khoản từ 6-100 ký tự")
    private String userName;
    private String password;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Không đúng định dạng email")
    private String email;
    @NotNull(message = "Tên người dùng không được để trống")
    private String fullName;
    private String phone;
    @NotNull(message = "Địa chỉ không được để trống")
    private String address;
    private boolean status;
}
