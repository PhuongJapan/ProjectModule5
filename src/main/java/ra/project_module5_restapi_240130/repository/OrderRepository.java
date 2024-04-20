package ra.project_module5_restapi_240130.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_module5_restapi_240130.dto.response.OrderDTOResponse;
import ra.project_module5_restapi_240130.model.EOrder;
import ra.project_module5_restapi_240130.model.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

        List<Order> findByStatus(EOrder status);
        List<Order> findByUserId(long userId);
        List<Order> findByUserIdAndStatus(long userId, EOrder status);

}
