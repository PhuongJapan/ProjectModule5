package ra.project_module5_restapi_240130.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_module5_restapi_240130.model.ShoppingCart;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository <ShoppingCart,Long>{
    List<ShoppingCart> findAllByUserIdAndProductId(long userId, long productId);
    List<ShoppingCart> findAllByUserId(long userId);
    Optional<ShoppingCart> findByUserIdAndId(long userId, long shopCartId);

}
