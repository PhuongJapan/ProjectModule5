package ra.project_module5_restapi_240130.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_id")
    private long id;
    @Column(name="user_name",columnDefinition = "varchar(100)",nullable = false)
    private String userName;
    @Column(columnDefinition = "varchar(255)",nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String fullName;
    @Column(name="user_status")
    private boolean status;
    @Column(columnDefinition = "varchar(255)",nullable = false)
    private String password;
    @Column(columnDefinition = "varchar(255)",nullable = true)
    private String avatar;
    @Column(unique = true)
    private String phone;
    @Column(columnDefinition = "varchar(255)",nullable = false)
    private String address;
//    @JsonFormat(pattern = "dd/mm/yyyy")
//    @CreationTimestamp
    private Date created_at;
//    @JsonFormat(pattern = "dd/mm/yyyy")
//    @CreationTimestamp
    private Date updated_at;
    @ManyToMany(fetch = FetchType.EAGER)
    //Tạo bảng thực thể yếu tên user_role
    @JoinTable(name="user_role",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns =@JoinColumn(name="role_id"))
    private Set<Roles> listRole;

}
