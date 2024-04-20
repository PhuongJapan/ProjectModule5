package ra.project_module5_restapi_240130.serviceImp;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.OrderDTORequest;
import ra.project_module5_restapi_240130.dto.request.OrderDTOUpdateRequest;
import ra.project_module5_restapi_240130.dto.response.OrderDTOResponse;
import ra.project_module5_restapi_240130.model.*;
import ra.project_module5_restapi_240130.repository.OrderRepository;
import ra.project_module5_restapi_240130.repository.ProductRepository;
import ra.project_module5_restapi_240130.repository.UserRepository;
import ra.project_module5_restapi_240130.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void cancelOrder(long userId, long orderId) {
        Order order= orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Id not found"));
        if(order.getUser().getId()==userId) {
            order.setStatus(EOrder.CANCEL);
            orderRepository.save(order);
            List<OrderDetail> orderDetailList = order.getOrderDetail();
            for (OrderDetail orderDetail : orderDetailList) {
                Product product = orderDetail.getProduct();
                product.setStock_quantity(product.getStock_quantity() + orderDetail.getQuantity());
                productRepository.save(product);
            }

        }else {
            throw new RuntimeException("Không có quyền hủy đơn hàng của user khác");
        }
    }

    @Override
    public List<OrderDTOResponse> findAll() {
        List<Order> orderList = orderRepository.findAll();
        return toOrderResponseList(orderList);
//        for (Order order: orderList){
//            OrderDTOResponse orderDTOResponse= OrderDTOResponse.builder()
//                    .id(order.getId())
//                    .serialNumber(order.getSerialNumber())
//                    .order_at(order.getOrder_at())
//                    .totalPrice(order.getTotalPrice())
//                    .status(order.getStatus().name())
//                    .note(order.getNote())
//                    .receiveName(order.getReceiveName())
//                    .receiveAddress(order.getReceiveAddress())
//                    .receivePhone(order.getReceivePhone())
//                    .created_at(order.getCreated_at())
//                    .received_at(order.getReceived_at())
//                    .build();
//            orderDTOResponseList.add(orderDTOResponse);
//        }
    }

    @Override
    public List<OrderDTOResponse> findByStatus(EOrder status) {
        List<Order> orderList = orderRepository.findByStatus(status);
        return toOrderResponseList(orderList);

    }

    @Override
    public Optional<OrderDTOResponse> findById(long orderId) {
        Optional<Order> orderOptional= orderRepository.findById(orderId);
        if(orderOptional.isPresent()){
            return toOrderResponseDTO(orderOptional.get());
        }else {
        throw new RuntimeException("Id not found");}

    }

    @Override
    public List<OrderDTOResponse> findByUserId(long userId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        if (!orderList.isEmpty()){
            return toOrderResponseList(orderList);
        }else {
            throw new IllegalArgumentException("Id not found");
        }
    }

    @Override
    public List<OrderDTOResponse> findByUserIdAndStatus(long userId, EOrder status) {
        List<Order> orderList = orderRepository.findByUserIdAndStatus(userId,status);
        if(!orderList.isEmpty()){
            return toOrderResponseList(orderList);
        }else {
            throw new RuntimeException("Id or status not found");
        }
    }

    @Override
    public void updateStatus(long orderId, OrderDTOUpdateRequest orderDTOUpdateRequest) {
        boolean checkExist = orderRepository.existsById(orderId);
        if(checkExist){
           Optional<Order> orderOptional = orderRepository.findById(orderId);
           orderOptional.get().setStatus(orderDTOUpdateRequest.getStatus());
           orderRepository.save(orderOptional.get());
        }else {
            throw new RuntimeException("Order not found");
        }
    }

    private List<OrderDTOResponse> toOrderResponseList(List<Order>list){
        return list.stream().map(order -> new OrderDTOResponse(
                order.getId(),
                order.getOrder_at(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getNote(),
                order.getReceiveName(),
                order.getReceiveAddress(),
                order.getReceivePhone(),
                order.getCreated_at(),
                order.getReceived_at(),
                order.getOrderDetail())).collect(Collectors.toList());
    }
    private Optional<OrderDTOResponse> toOrderResponseDTO(Order order){
        return Optional.of(OrderDTOResponse.builder()
                    .id(order.getId())
                    .order_at(order.getOrder_at())
                    .totalPrice(order.getTotalPrice())
                    .status(order.getStatus().name())
                    .note(order.getNote())
                    .receiveName(order.getReceiveName())
                    .receiveAddress(order.getReceiveAddress())
                    .receivePhone(order.getReceivePhone())
                    .created_at(order.getCreated_at())
                    .received_at(order.getReceived_at())
                    .orderDetailList(order.getOrderDetail())
                    .build());
    }


}
