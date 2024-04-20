package ra.project_module5_restapi_240130.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.WishlistDTORequest;
import ra.project_module5_restapi_240130.model.Product;
import ra.project_module5_restapi_240130.model.User;
import ra.project_module5_restapi_240130.model.WishList;
import ra.project_module5_restapi_240130.repository.ProductRepository;
import ra.project_module5_restapi_240130.repository.UserRepository;
import ra.project_module5_restapi_240130.repository.WishListRepository;
import ra.project_module5_restapi_240130.service.WishListService;

import java.util.List;
import java.util.Optional;

@Service
public class WishListServiceImp implements WishListService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Override
    public List<WishList> findAll(long userId) {

        return wishListRepository.findAllByUserId(userId);
    }

    @Override
    public List<WishList> findAllWistList() {
        return wishListRepository.findAll();
    }

    @Override
    public WishList save(WishlistDTORequest wishlistDTORequest) {
        Product product= productRepository.findById(wishlistDTORequest.getProductId()).get();
        User user = userRepository.findById(wishlistDTORequest.getUserId()).get();
        WishList wishList = new WishList();
        wishList.setUser(user);
        wishList.setProduct(product);
        return wishListRepository.save(wishList);
    }
    @Override
    public void delete(long wishlistId) {
        Optional<WishList> wishListOpt = wishListRepository.findById(wishlistId);
        if(wishListOpt.isPresent()){
            wishListRepository.deleteById(wishlistId);
        }else {
            throw new RuntimeException("Id not found");
        }
    }
}
