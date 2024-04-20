package ra.project_module5_restapi_240130.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.OrderDTORequest;
import ra.project_module5_restapi_240130.dto.request.OrderDTOUpdateRequest;
import ra.project_module5_restapi_240130.dto.response.OrderDTOResponse;
import ra.project_module5_restapi_240130.model.EOrder;
import ra.project_module5_restapi_240130.model.Order;
import ra.project_module5_restapi_240130.model.OrderDetail;

import java.util.List;
import java.util.Optional;


public interface OrderService{
    List<OrderDTOResponse> findAll();
    List<OrderDTOResponse> findByStatus(EOrder status);
    Optional<OrderDTOResponse> findById(long orderId);
    List<OrderDTOResponse> findByUserId(long userId);
    List<OrderDTOResponse> findByUserIdAndStatus(long userId, EOrder status);
    void updateStatus(long orderId, OrderDTOUpdateRequest orderDTOUpdateRequest);
    void cancelOrder(long userId, long orderId);


}
