package ra.project_module5_restapi_240130.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private long id;
    //code san pham
    @Column(name="sku",columnDefinition = "varchar(100)", unique = true)
    private String sku;
    @Column(name="product_name", columnDefinition = "varchar(100)",nullable = false,unique = true)
    private String name;
    @Column(columnDefinition = "text")
    //neu ko dat dieu kien text thi chi duoc 255 ky tu
    private String descriptions;
    private double unit_price;
    private int stock_quantity;
    @Column(columnDefinition = "varchar(255)")
    private String image;
    private boolean status;
//    @JsonFormat(pattern = "dd/mm/yyy")
//    @CreationTimestamp
    private Date created_at;
    private Date updated_at;
    @ManyToOne
    @JoinColumn(name="catalog_id",referencedColumnName = "catalog_id")
    private Categories catalog;


}
