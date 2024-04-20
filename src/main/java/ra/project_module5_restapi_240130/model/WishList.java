package ra.project_module5_restapi_240130.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="wish_list")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="product_id",referencedColumnName = "product_id")
    private Product product;
}
