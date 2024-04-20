package ra.project_module5_restapi_240130.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_module5_restapi_240130.dto.request.OrderDTORequest;
import ra.project_module5_restapi_240130.dto.request.ShoppingCartDTORequest;
import ra.project_module5_restapi_240130.dto.request.ShoppingCartUpdateQuantityRequest;
import ra.project_module5_restapi_240130.dto.response.ShoppingCartDTOResponse;
import ra.project_module5_restapi_240130.service.OrderService;
import ra.project_module5_restapi_240130.service.ProductService;
import ra.project_module5_restapi_240130.service.ShoppingCartService;
import ra.project_module5_restapi_240130.service.UserService;

@RestController
@RequestMapping("api/v1/")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @PostMapping("user/shoppingCart")
    public ResponseEntity<?> save(@Valid  @RequestBody ShoppingCartDTORequest shoppingCartDTORequest){
        return new ResponseEntity<>(shoppingCartService.save(shoppingCartDTORequest), HttpStatus.CREATED);
    }
    @GetMapping("user/shoppingCart/{id}")
    public ResponseEntity<?> findAll(@PathVariable long id){
        return new ResponseEntity<>(shoppingCartService.findAllByUserId(id),HttpStatus.OK);

    }

    @PutMapping("user/shoppingCart/{userId}/update/{productId}")
    public ResponseEntity<?> updateQuantity (@PathVariable long userId, @PathVariable long productId,@RequestBody ShoppingCartUpdateQuantityRequest shopUpdateQuantity){
//        return ResponseEntity.ok("update thanh cong");
        ShoppingCartDTOResponse shoppingCartRs = shoppingCartService.updateQuantity(shopUpdateQuantity,userId,productId);
        if(shoppingCartRs==null){
            return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(shoppingCartRs,HttpStatus.OK);
        }
    }
    @DeleteMapping("user/shoppingCart/{userId}/delete/{shopCartId}")
    public ResponseEntity<?> deleteOneProduct(@PathVariable long userId, @PathVariable long shopCartId){
        if(shoppingCartService.deleteOneProduct(userId,shopCartId)){
            return ResponseEntity.ok("Delete successfully");
        }return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("user/shoppingCart/{userId}/deleteAll")
    public ResponseEntity<?> deleteAll(@PathVariable long userId){
        if (shoppingCartService.deleteAllProduct(userId)){
            return ResponseEntity.ok("Delete successfully");

        }return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
    }
    @PostMapping("user/shoppingCart/checkOut")
    public ResponseEntity<?> checkOutShopCart(@RequestBody OrderDTORequest orderDTORequest){
        shoppingCartService.checkOut(orderDTORequest);
        return new ResponseEntity<>("Order successfully",HttpStatus.CREATED);
    }

}
