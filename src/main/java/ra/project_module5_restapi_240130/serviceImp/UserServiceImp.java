package ra.project_module5_restapi_240130.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.*;
import ra.project_module5_restapi_240130.dto.response.ProductDTOResponse;
import ra.project_module5_restapi_240130.dto.response.SignInResponse;
import ra.project_module5_restapi_240130.dto.response.SignUpResponse;
import ra.project_module5_restapi_240130.dto.response.UserResponse;
import ra.project_module5_restapi_240130.mapper.UserMapper;
import ra.project_module5_restapi_240130.model.ERoles;
import ra.project_module5_restapi_240130.model.Product;
import ra.project_module5_restapi_240130.model.Roles;
import ra.project_module5_restapi_240130.model.User;
import ra.project_module5_restapi_240130.repository.RolesRepository;
import ra.project_module5_restapi_240130.repository.UserRepository;
import ra.project_module5_restapi_240130.security.jwt.JwtProvider;
import ra.project_module5_restapi_240130.security.principle.CustomUserDetail;
import ra.project_module5_restapi_240130.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public SignUpResponse register(SignUpRequest signUpRequest) {
        Set<Roles> setRoles = new HashSet<>();
        signUpRequest.getListRole().forEach(role->{
            //admin,moderator,user
            switch (role){
                case "admin":
                    setRoles.add(rolesRepository.findByName(ERoles.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Không tồn tại quyền admin")));
                    break;
//                case "moderator":
//                    setRoles.add(rolesRepository.findByName(ERoles.ROLE_MODERATOR)
//                            .orElseThrow(() -> new RuntimeException("Không tồn tại quyền moderator")));
//                    break;
                case "user":
                default:
                    setRoles.add(rolesRepository.findByName(ERoles.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Không tồn tại quyền user")));

            }
        });
        User user = modelMapper.map(signUpRequest, User.class);
        user.setListRole(setRoles);
        //Mã hóa mật khẩu khi đăng ký
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //Khi đăng ký, set status mặc định là true
        user.setStatus(true);
        //Thực hiện thêm mới
        User userCreated = usersRepository.save(user);
        SignUpResponse signUpResponse = modelMapper.map(userCreated, SignUpResponse.class);
        //set lại quyền user trả về
        List<String> listUserRoles = new ArrayList<>();
        userCreated.getListRole().stream().forEach(roles -> {
            listUserRoles.add(roles.getName().name());
        });
        signUpResponse.setListRole(listUserRoles);
        return signUpResponse;
    }
    @Override
    public SignInResponse login(SignInRequest signInRequest) {
        //1. Lấy User trong db ra và so sánh(mã hóa mật khẩu Bcryt)
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUserName(), signInRequest.getPassword()));
        } catch (Exception ex) {
            throw new RuntimeException("Username or Password incorrect");
        }
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(userDetails);
        String refreshToken = jwtProvider.generateRefreshToken(userDetails);
        return new SignInResponse(userDetails.getUsername(),userDetails.getPassword(),
                userDetails.getEmail(),userDetails.getFullName(),
                userDetails.getAddress(),
                userDetails.getAuthorities(),
                accessToken,refreshToken);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> listUser = usersRepository.findAll();
        List<UserResponse> listUserResponse = listUser.stream()
                .map(user-> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
        return listUserResponse;
    }

    @Override
    public List<Roles> findAllByListRole() {
        List<Roles> listRoles = rolesRepository.findAll();
        return listRoles;
    }


    @Override
    public boolean updateStatus(long id) {
        boolean checkExist = usersRepository.existsById(id);
        if(checkExist){
            Optional<User> user =usersRepository.findById(id);
            user.get().setStatus(!user.get().isStatus());
            usersRepository.save(user.get());
            return true;
        }
        return false;
    }

    @Override
    public UserResponse update(UserUpdateDTORequest userUpdateDTORequest, long id) {
        boolean checkExist = usersRepository.existsById(id);
        //Tồn tại thì cập nhật
        if(checkExist){
            User user = usersRepository.findById(id).get();
            //Do Dtorequest ko có ID nên phải chuyển đổi setID
            user.setId(id);
            user.setEmail(userUpdateDTORequest.getEmail());
            user.setFullName(userUpdateDTORequest.getFullName());
            user.setPhone(userUpdateDTORequest.getPhone());
            user.setAddress(userUpdateDTORequest.getAddress());
            user.setStatus(userUpdateDTORequest.isStatus());
            User userUpdate = usersRepository.save(user);
            return userMapper.mapperEntityToResponse(userUpdate);
        }
        return null;
    }

    @Override
    public UserResponse findById(long id) {
        Optional<User> optionalUser= usersRepository.findById(id);
        if (optionalUser.isPresent()){
            return userMapper.mapperEntityToResponse(optionalUser.get());
        }
        return null;
    }

    @Override
    public List<UserResponse> find(int page, int size, String direction, String orderBy) {
        return null;
    }

    @Override
    public List<UserResponse> searchUserByFullName(String fullName) {
        List<User> users= usersRepository.findAllByFullNameContainingIgnoreCase(fullName);
        return users.stream().map(user ->userMapper.mapperEntityToResponse(user)).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> findAllUsers(String direction, String orderBy, int page, int size) {
        PageRequest pageable;
        if(direction.equals("ASC")){
            pageable= PageRequest.of(page,size, Sort.by(orderBy).ascending());
        }else {
            pageable = PageRequest.of(page,size,Sort.by(orderBy).descending());
        }
        List<User> listUser = usersRepository.findAll(pageable).getContent();
        return listUser.stream()
                .map(user -> userMapper.mapperEntityToResponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public void changePass(UserChangePasswordRequest userChange, long id) {
        Optional<User> optionalUser= usersRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if(passwordEncoder.matches(userChange.getOldPass(), user.getPassword())&&userChange.getNewPass().equals(userChange.getConfirmNewPass())){
                user.setPassword(passwordEncoder.encode(userChange.getNewPass()));
                usersRepository.save(user);
            }else {
                throw new RuntimeException("Mật khẩu không đúng");
            };
        }else {
            throw new RuntimeException("Id not found");
        }
    }

    @Override
    public UserResponse addRoleForAdmin(long userId, long roleId) {
        //roleId: là role muốn add vào
        Optional<User> userOptional = usersRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            Optional<Roles> rolesOptional = rolesRepository.findById(roleId);
            if(rolesOptional.isPresent()){
                user.getListRole().add(rolesOptional.get());
                return userMapper.mapperEntityToResponse( usersRepository.save(user));
            }else {
                throw new RuntimeException("Id not found");
            }
        }else {
            throw new RuntimeException("Id not found");
        }
    }
    @Override
    public UserResponse deleteRoleForAdmin(long userId, long roleId) {
        //roleId: là role muốn add vào
        Optional<User> userOptional = usersRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            Optional<Roles> rolesOptional = rolesRepository.findById(roleId);
            if(rolesOptional.isPresent()){
                user.getListRole().remove(rolesOptional.get());
                return userMapper.mapperEntityToResponse( usersRepository.save(user));
            }else {
                throw new RuntimeException("Id not found");
            }
        }else {
            throw new RuntimeException("Id not found");
        }
    }
}
