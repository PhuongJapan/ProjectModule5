package ra.project_module5_restapi_240130.service;

import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.WishlistDTORequest;
import ra.project_module5_restapi_240130.model.WishList;

import java.util.List;

@Service
public interface WishListService {
    List<WishList> findAll(long userId);
    List<WishList> findAllWistList();

    WishList save(WishlistDTORequest wishlistDTORequest);
    void delete(long wishlistId);

}
