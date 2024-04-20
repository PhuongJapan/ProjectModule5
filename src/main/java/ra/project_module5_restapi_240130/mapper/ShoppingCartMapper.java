package ra.project_module5_restapi_240130.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.project_module5_restapi_240130.dto.request.ProductDTORequest;
import ra.project_module5_restapi_240130.dto.request.ShoppingCartDTORequest;
import ra.project_module5_restapi_240130.dto.request.UserDTORequest;
import ra.project_module5_restapi_240130.dto.response.ProductDTOResponse;
import ra.project_module5_restapi_240130.dto.response.ShoppingCartDTOResponse;
import ra.project_module5_restapi_240130.model.Product;
import ra.project_module5_restapi_240130.model.ShoppingCart;
import ra.project_module5_restapi_240130.model.User;
import ra.project_module5_restapi_240130.repository.ProductRepository;
import ra.project_module5_restapi_240130.repository.UserRepository;

@Component
public class ShoppingCartMapper implements GenericMapper<ShoppingCart, ShoppingCartDTORequest, ShoppingCartDTOResponse>{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public ShoppingCart mapperRequestToEntity(ShoppingCartDTORequest shoppingCartDTORequest) {
        return ShoppingCart.builder()
                .quantity(shoppingCartDTORequest.getQuantity())
                .user(userRepository.findById(shoppingCartDTORequest.getUserId()).orElse(null))
                .product(productRepository.findById(shoppingCartDTORequest.getProductId()).orElse(null)).build();
    }

    @Override
    public ShoppingCartDTOResponse mapperEntityToResponse(ShoppingCart shoppingCart) {
        return new ShoppingCartDTOResponse(
                shoppingCart.getId(),
                shoppingCart.getUser().getId(),
                shoppingCart.getUser().getFullName(),
                shoppingCart.getProduct().getId(),
                shoppingCart.getProduct().getName(),
                shoppingCart.getQuantity()
        );
    }

}
