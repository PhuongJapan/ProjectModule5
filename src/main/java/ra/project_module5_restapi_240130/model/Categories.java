package ra.project_module5_restapi_240130.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="catalog_id")
    private long id;
    @Column(name="catalog_name",columnDefinition = "varchar(100)",nullable = false)
    private String name;
    private String descriptions;
    @Column(name="catalog_status")
    private boolean status;

    @OneToMany(mappedBy = "catalog")
    @JsonIgnore
    private List<Product> listProduct = new ArrayList<>();

}
