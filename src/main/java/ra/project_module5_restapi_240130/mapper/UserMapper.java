package ra.project_module5_restapi_240130.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.project_module5_restapi_240130.dto.request.CategoriesDTORequest;
import ra.project_module5_restapi_240130.dto.request.UserDTORequest;
import ra.project_module5_restapi_240130.dto.response.CategoriesDTOResponse;
import ra.project_module5_restapi_240130.dto.response.UserResponse;
import ra.project_module5_restapi_240130.model.Categories;
import ra.project_module5_restapi_240130.model.Roles;
import ra.project_module5_restapi_240130.model.User;
import ra.project_module5_restapi_240130.repository.UserRepository;

import java.util.stream.Collectors;

@Component
public class UserMapper implements GenericMapper<User, UserDTORequest, UserResponse> {

    @Override
    public User mapperRequestToEntity(UserDTORequest userDTORequest) {
        return User.builder()
                .userName(userDTORequest.getUserName())
                .password(userDTORequest.getPassword())
                .email(userDTORequest.getEmail())
                .address(userDTORequest.getAddress())
                .phone(userDTORequest.getPhone())
                .fullName(userDTORequest.getFullName())
                .status(true)
                .build();
    }

    @Override
    public UserResponse mapperEntityToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getFullName(),
                user.getAddress(),
                user.isStatus(),
                user.getPhone(),
                user.getListRole().stream().map(roles -> roles.getName().name()).collect(Collectors.toList())
        );
    }
}
