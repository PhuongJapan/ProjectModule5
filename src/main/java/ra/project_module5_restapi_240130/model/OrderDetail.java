package ra.project_module5_restapi_240130.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="order_detail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_detail_id")
    private long id;
    private double unit_price;
    private int quantity;
    @Column(columnDefinition = "varchar(100)")
    private String name;

    @ManyToOne
    @JoinColumn(name="order_id",referencedColumnName = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id",referencedColumnName = "product_id")
    private Product product;

    
}
