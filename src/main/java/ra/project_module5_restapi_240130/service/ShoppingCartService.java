package ra.project_module5_restapi_240130.service;

import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.OrderDTORequest;
import ra.project_module5_restapi_240130.dto.request.ShoppingCartDTORequest;
import ra.project_module5_restapi_240130.dto.request.ShoppingCartUpdateQuantityRequest;
import ra.project_module5_restapi_240130.dto.response.ShoppingCartDTOResponse;

import java.util.List;

@Service
public interface ShoppingCartService {
    ShoppingCartDTOResponse save(ShoppingCartDTORequest shoppingCartDTORequest);
    List<ShoppingCartDTOResponse> findAllByUserId(long userId);
    ShoppingCartDTOResponse updateQuantity (ShoppingCartUpdateQuantityRequest shopUpdateQuantity, long userId, long productId);
    boolean deleteOneProduct (long userId, long shopCartId);
    boolean deleteAllProduct (long userId);
    boolean checkOut (OrderDTORequest orderDTORequest);
    }
