package ra.project_module5_restapi_240130.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private long id;
    @Column(name="role_name")
    @Enumerated(EnumType.STRING)
    //khai báo Erole để khi nhận chỉ được 1 trong các giá trị admin hay khác
    private ERoles name;
}
