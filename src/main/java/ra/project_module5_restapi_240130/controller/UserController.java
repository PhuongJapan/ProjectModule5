package ra.project_module5_restapi_240130.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_module5_restapi_240130.dto.request.*;
import ra.project_module5_restapi_240130.dto.response.*;
import ra.project_module5_restapi_240130.model.ERoles;
import ra.project_module5_restapi_240130.model.Roles;
import ra.project_module5_restapi_240130.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
//Đường dẫn cho tất cả mọi người truy cập = public
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("public/auth/sign-up")
    public ResponseEntity<SignUpResponse> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = userService.register(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }

    @PostMapping("public/auth/login")
    public ResponseEntity<SignInResponse> login(@Valid @RequestBody SignInRequest signInRequest) {
        return new ResponseEntity<>(userService.login(signInRequest), HttpStatus.OK);
    }
    @GetMapping("admin/user")
    public ResponseEntity<List<UserResponse>> findALl(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }

    @PutMapping("admin/user/{id}")
    public ResponseEntity<Message> updateStatus(@PathVariable long id) {
        boolean result = userService.updateStatus(id);
        if (result) {
            return new ResponseEntity<>(new Message("Update status successful"), HttpStatus.OK);
            //Do no content nên sẽ ko hiện ra nội dung gì
        } else {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("admin/users")
    public ResponseEntity<List<UserResponse>> find(
            @RequestParam(defaultValue = "0") int page,
            //Nếu ko cho giá trị page thì mặc định page bằng 0
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "fullName") String orderBy
            //Nếu ko truyền vào tiêu chí tìm thì mặc định tìm theo name
    ) {
        List<UserResponse> listUser = userService.findAllUsers(direction, orderBy,page, size);
        return new ResponseEntity<>(listUser, HttpStatus.OK);
    }
    @GetMapping("admin/roles")
    public ResponseEntity<List<Roles>>findAll(){
        return new ResponseEntity<>(userService.findAllByListRole(),HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> findByIdUser(@PathVariable long id) {
        UserResponse user = userService.findById(id);
        if (user==null){
            //id ko đúng
            return new ResponseEntity<>(new Message("Id not found"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(user,HttpStatus.OK);
        }

    }
    //Thay đổi pass cho User
    @PutMapping("user/{id}/changePass")
    public ResponseEntity<?>changePass(@RequestBody UserChangePasswordRequest userChange,@PathVariable long id){
            userService.changePass(userChange,id);
            return new ResponseEntity<>("Thay đổi mật khẩu thành công",HttpStatus.OK);
    }
    //UPdate thông tin user
    @PutMapping("user/update/{id}")
    public ResponseEntity<?>update(@RequestBody UserUpdateDTORequest userUpdateDTORequest, @PathVariable long id){
        UserResponse user = userService.update(userUpdateDTORequest,id);
        if(user==null){
            return new ResponseEntity<>(new Message("Id not found"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
    }
    //Tìm User theo fullName
    @GetMapping("admin/user/search")
    public ResponseEntity<?> searchByFullName(
            // params truyền đúng tên biến ở controller
            @RequestParam(defaultValue = "") String fullName)
    {
        return new ResponseEntity<>(userService.searchUserByFullName(fullName),HttpStatus.OK);
    }
    //Add role cho User
    @PostMapping("admin/{userId}/role")
    //Test gửi request trong Params
    public ResponseEntity<?>addRole(@RequestParam(defaultValue = "")long roleId,@PathVariable long userId){
            return new ResponseEntity<>(userService.addRoleForAdmin(userId,roleId),HttpStatus.CREATED);
    }
    @DeleteMapping("admin/{userId}/role")
    public ResponseEntity<?>deleteRole(@RequestParam(defaultValue = "")long roleId,@PathVariable long userId){
        return new ResponseEntity<>(userService.deleteRoleForAdmin(userId,roleId),HttpStatus.OK);
    }




}

