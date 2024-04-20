package ra.project_module5_restapi_240130.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_module5_restapi_240130.dto.request.WishlistDTORequest;
import ra.project_module5_restapi_240130.model.WishList;
import ra.project_module5_restapi_240130.service.WishListService;

@RestController
@RequestMapping("api/v1/")
public class WishListController {
    @Autowired
    private WishListService wishListService;
    @GetMapping("user/wishlist/{id}")
    public ResponseEntity<?> findAllWishlistByUserId(@PathVariable long id){
     return new ResponseEntity<>(wishListService.findAll(id), HttpStatus.OK);
    }
    @GetMapping("user/wishlist")
    public ResponseEntity<?> findAllWishlist(){
        return new ResponseEntity<>(wishListService.findAllWistList(), HttpStatus.OK);
    }
    @PostMapping("user/wishlist/save")
    public ResponseEntity<?> save(@Valid @RequestBody WishlistDTORequest wishlistDTORequest){
        return new ResponseEntity<>(wishListService.save(wishlistDTORequest),HttpStatus.CREATED
        );
    }
    @DeleteMapping("user/wishlist/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable long id){
        wishListService.delete(id);
        return new ResponseEntity<>("Đã xóa thành công",HttpStatus.OK);
    }



}
