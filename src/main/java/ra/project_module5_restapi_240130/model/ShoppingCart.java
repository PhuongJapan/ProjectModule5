package ra.project_module5_restapi_240130.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="shopping_cart")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name="product_id",referencedColumnName = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;


}
