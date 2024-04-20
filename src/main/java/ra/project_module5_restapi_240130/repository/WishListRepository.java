package ra.project_module5_restapi_240130.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.project_module5_restapi_240130.model.Product;
import ra.project_module5_restapi_240130.model.WishList;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long> {
    List<WishList> findAllByUserId(long userId);
    @Query("select wl.product from WishList wl group by wl.product.id order by count (wl.user.id)DESC")
    List<Product> getAllFeaturedProducts();
}
