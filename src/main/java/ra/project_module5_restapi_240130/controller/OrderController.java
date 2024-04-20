package ra.project_module5_restapi_240130.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_module5_restapi_240130.dto.request.OrderDTORequest;
import ra.project_module5_restapi_240130.dto.request.OrderDTOUpdateRequest;
import ra.project_module5_restapi_240130.dto.response.OrderDTOResponse;
import ra.project_module5_restapi_240130.model.EOrder;
import ra.project_module5_restapi_240130.model.Order;
import ra.project_module5_restapi_240130.service.OrderService;
import ra.project_module5_restapi_240130.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @GetMapping("admin/orders")
    public ResponseEntity<List<OrderDTOResponse>> findAll(){
//        List<OrderDTOResponse> orderDTOResponseList = orderService.findAll();
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }
    @GetMapping("admin/orders/status/{status}")
    public ResponseEntity<?> findAllByStatus(@PathVariable EOrder status){
        List<OrderDTOResponse> listOrder = orderService.findByStatus(status);
            return new ResponseEntity<>(listOrder,HttpStatus.OK);}


    @GetMapping("admin/orders/{orderId}")
    public ResponseEntity<Optional<OrderDTOResponse>> findById(@PathVariable long orderId){
//        Optional<OrderDTOResponse> orderOpt = orderService.findById(orderId);
            return new ResponseEntity<>(orderService.findById(orderId),HttpStatus.OK);
    }
    @PutMapping("admin/orders/{orderId}/status")
    public ResponseEntity<?> updateStatus(@RequestBody OrderDTOUpdateRequest orderDTOUpdateRequest, @PathVariable long orderId){
        orderService.updateStatus(orderId,orderDTOUpdateRequest);
        return new ResponseEntity<>("Update successfully",HttpStatus.OK);
    }
    @GetMapping("user/orders/{userId}/history")
    public ResponseEntity<?> findOrdersByUserId(@PathVariable long userId){
        return new ResponseEntity<>(orderService.findByUserId(userId),HttpStatus.OK);
    }
    @GetMapping("user/{userId}/history/{status}")
    public ResponseEntity<?> findOrdersByUserIdAndStatus(@PathVariable long userId,@PathVariable EOrder status){

        return new ResponseEntity<>(orderService.findByUserIdAndStatus(userId,status),HttpStatus.OK);
    }
    @PutMapping("user/orders/{userId}/history/{orderId}")
    public ResponseEntity<?> cancelOrderByUserIdAndOrderId(@PathVariable long userId, @PathVariable long orderId){
        orderService.cancelOrder(userId, orderId);
        return new ResponseEntity<>("Cancel successfully",HttpStatus.OK);
    }


}