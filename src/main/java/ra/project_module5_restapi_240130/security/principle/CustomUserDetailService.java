package ra.project_module5_restapi_240130.security.principle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.model.User;
import ra.project_module5_restapi_240130.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. Lấy User từ db theo userName đăng nhập
        User user = userRepository.findByUserNameAndStatus(username,true)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        //2. Lấy các quyền của user
        List<GrantedAuthority> authorities = user.getListRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        //3. Trả về đối tượng UserDetail
        return CustomUserDetail.builder().id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .address(user.getAddress())
                .authorities(authorities).build();
    }
}
