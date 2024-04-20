package ra.project_module5_restapi_240130.service;

import ra.project_module5_restapi_240130.dto.request.*;
import ra.project_module5_restapi_240130.dto.response.ProductDTOResponse;
import ra.project_module5_restapi_240130.dto.response.SignInResponse;
import ra.project_module5_restapi_240130.dto.response.SignUpResponse;
import ra.project_module5_restapi_240130.dto.response.UserResponse;
import ra.project_module5_restapi_240130.model.ERoles;
import ra.project_module5_restapi_240130.model.Roles;
import ra.project_module5_restapi_240130.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest);
    SignInResponse login(SignInRequest signInRequest);
    List<UserResponse> findAll();
//    List<UserResponse> findAllRoles(ERoles eRoles);
    List<Roles> findAllByListRole();
    boolean updateStatus(long id);
    UserResponse update(UserUpdateDTORequest userUpdateDTORequest, long id);
    UserResponse findById(long id);
    List<UserResponse> find(int page, int size,String direction, String orderBy);

    List<UserResponse> searchUserByFullName(String fullName);

    List<UserResponse> findAllUsers(String direction, String orderBy, int page, int size);
    void changePass(UserChangePasswordRequest userChange,long id);
    UserResponse addRoleForAdmin(long userId, long roleId);
    UserResponse deleteRoleForAdmin(long userId, long roleId);
}
