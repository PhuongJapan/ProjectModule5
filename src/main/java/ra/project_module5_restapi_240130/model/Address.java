package ra.project_module5_restapi_240130.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "varchar(255)")
    private String full_address;
    @Column(columnDefinition = "varchar(15)")
    private String phone;
    private String receive_name;
    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;
}
