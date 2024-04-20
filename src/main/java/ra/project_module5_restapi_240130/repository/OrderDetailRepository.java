package ra.project_module5_restapi_240130.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.project_module5_restapi_240130.model.OrderDetail;
import ra.project_module5_restapi_240130.model.Product;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    @Query("select od.product from OrderDetail od group by od.product.id order by sum(od.quantity)DESC")
    List<Product> getAllByBestSellerProducts();
}
