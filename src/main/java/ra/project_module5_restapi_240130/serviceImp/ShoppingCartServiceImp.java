package ra.project_module5_restapi_240130.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.OrderDTORequest;
import ra.project_module5_restapi_240130.dto.request.ShoppingCartDTORequest;
import ra.project_module5_restapi_240130.dto.request.ShoppingCartUpdateQuantityRequest;
import ra.project_module5_restapi_240130.dto.response.ShoppingCartDTOResponse;
import ra.project_module5_restapi_240130.mapper.ShoppingCartMapper;
import ra.project_module5_restapi_240130.model.*;
import ra.project_module5_restapi_240130.repository.*;
import ra.project_module5_restapi_240130.service.AddressService;
import ra.project_module5_restapi_240130.service.ShoppingCartService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImp implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public ShoppingCartDTOResponse save(ShoppingCartDTORequest shoppingCartDTORequest) {
        List<ShoppingCart> shoppingCart= shoppingCartRepository.findAllByUserIdAndProductId(shoppingCartDTORequest.getUserId(),shoppingCartDTORequest.getProductId());
        if(!shoppingCart.isEmpty()) {
            ShoppingCart existCart = shoppingCart.get(0);
            existCart.setQuantity(existCart.getQuantity() + shoppingCartDTORequest.getQuantity());
            return shoppingCartMapper.mapperEntityToResponse(shoppingCartRepository.save(existCart));
        }else {
            Optional<User> userOpt = userRepository.findById(shoppingCartDTORequest.getUserId());
            Optional<Product> productOpt = productRepository.findById(shoppingCartDTORequest.getProductId());
            if(userOpt.isPresent()&& productOpt.isPresent()){
                ShoppingCart newCart = shoppingCartMapper.mapperRequestToEntity(shoppingCartDTORequest);
                return shoppingCartMapper.mapperEntityToResponse(shoppingCartRepository.save(newCart));
            }else {
                throw new RuntimeException("user or product Id not found");
            }
        }
    }

    @Override
    public List<ShoppingCartDTOResponse> findAllByUserId(long userId) {
        //test bang userId : 6
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUserId(userId);
        return shoppingCarts.stream()
                .map(shoppingCart -> shoppingCartMapper.mapperEntityToResponse(shoppingCart)).collect(Collectors.toList());
    }

    @Override
    public ShoppingCartDTOResponse updateQuantity(ShoppingCartUpdateQuantityRequest shopUpdateQuantity, long userId, long productId) {
        List<ShoppingCart> shoppingCart = shoppingCartRepository.findAllByUserIdAndProductId(userId,productId);
        if(!shoppingCart.isEmpty()){
            ShoppingCart existCart = shoppingCart.get(0);
            existCart.setQuantity(shopUpdateQuantity.getQuantity());
            return shoppingCartMapper.mapperEntityToResponse(shoppingCartRepository.save(existCart));
        } else {
           return null;
        }
    }

    @Override
    public boolean deleteOneProduct(long userId, long shopCartId) {
//        List<ShoppingCart> shoppingCartOpt = shoppingCartRepository.findAllByUserId(userId);
//        if (!shoppingCartOpt.isEmpty()){
//            shoppingCartRepository.deleteById(shopCartId);
//        }else {
//            throw new RuntimeException("Id not found");
//        }
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserIdAndId(userId, shopCartId).orElseThrow(null);
        //Cach viet cho optional de check null
        if (shoppingCart != null) {
            shoppingCartRepository.delete(shoppingCart);
            return true;
        }return false;
    }

    @Override
    public boolean checkOut(OrderDTORequest orderDTORequest) {
        //LUU Y CACH VIET ELSE THROW
        //Bước 1: Tìm thông tin User, address
        User user = userRepository.findById(orderDTORequest.getUserId()).orElseThrow(()->new RuntimeException("UserId not found"));
        Address address= addressRepository.findById(orderDTORequest.getAddressId()).orElseThrow(()->new RuntimeException("AddressId not found"));
        //Bước 2: Chuyển đổi từ listShopCartId sang list đối tượng ShopCart
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        for(Long shopCartId: orderDTORequest.getListShopCartId()){
            ShoppingCart shopCart = shoppingCartRepository.findById(shopCartId).get();
            if(shopCart.getQuantity()>shopCart.getProduct().getStock_quantity()){
                throw new RuntimeException("Số lượng đặt hàng vượt quá số lượng tồn kho");
            }
            shoppingCartList.add(shopCart);

        }

        //Tính tổng tiền đơn hàng
        double sum = 0;
        for(ShoppingCart shoppingCart: shoppingCartList){
            sum= sum+(shoppingCart.getQuantity()*shoppingCart.getProduct().getUnit_price());
        }
        //Tạo đơn hàng với các thông tin cần thiết
        Order order= Order.builder()
                .user(user)
                .order_at(new Date())
                .created_at(new Date())
                .note(orderDTORequest.getNote())
                .receiveAddress(address.getFull_address())
                .receiveName(address.getReceive_name())
                .receivePhone(address.getPhone())
                //Cách tính ngày receive sẽ hơn ngày order 4 ngày= tính bằng mili giây
                .received_at(new Date(new Date().getTime()+4*24*60*60*1000))
                .status(EOrder.WAITING)
                //HOI MR.BINH
                .totalPrice(sum)
                .build();
        //Lưu đối tượng
        Order newOrder = orderRepository.save(order);
        //Chuyển đổi Shopping Cart sang OrderDetail
        for(ShoppingCart shoppingCart: shoppingCartList){
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(newOrder)
                    .product(shoppingCart.getProduct())
                    .name(shoppingCart.getProduct().getName())
                    .unit_price(shoppingCart.getProduct().getUnit_price())
                    .quantity(shoppingCart.getQuantity())
                    .build();
            orderDetailRepository.save(orderDetail);
        }
        //Bước 3: Xóa những shopping Cart khi check out
        for(Long shopCartId: orderDTORequest.getListShopCartId()){
            shoppingCartRepository.deleteById(shopCartId);
        }
        //Bước 4: TRừ đi số lượng ở product (for qua đối tượng ShoppingCart)
        for(ShoppingCart shoppingCart:shoppingCartList){
            Product product = shoppingCart.getProduct();
            product.setStock_quantity(product.getStock_quantity()-shoppingCart.getQuantity());
            productRepository.save(product);
        }
        return true;
    }

    @Override
    public boolean deleteAllProduct(long userId) {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUserId(userId);
        if(!shoppingCarts.isEmpty()){
            shoppingCartRepository.deleteAll(shoppingCarts);
            return true;
        }return false;
    }

}
