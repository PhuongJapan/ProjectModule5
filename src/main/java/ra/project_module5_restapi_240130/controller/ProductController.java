package ra.project_module5_restapi_240130.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_module5_restapi_240130.dto.request.CategoriesDTORequest;
import ra.project_module5_restapi_240130.dto.request.ProductDTORequest;
import ra.project_module5_restapi_240130.dto.request.ProductDTOUpdateRequest;
import ra.project_module5_restapi_240130.dto.response.CategoriesDTOResponse;
import ra.project_module5_restapi_240130.dto.response.Message;
import ra.project_module5_restapi_240130.dto.response.ProductDTOResponse;
import ra.project_module5_restapi_240130.repository.OrderRepository;
import ra.project_module5_restapi_240130.service.CategoriesService;
import ra.project_module5_restapi_240130.service.OrderService;
import ra.project_module5_restapi_240130.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
public class ProductController {
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @GetMapping("admin/product")
    public ResponseEntity<List<ProductDTOResponse>> findAll(){
        List<ProductDTOResponse> listPro = productService.findAll();
        return new ResponseEntity<>(listPro, HttpStatus.OK);
    }
//    public ResponseEntity<Map<String, Object>> findAll(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size) {
//        return new ResponseEntity<>(productService.findAll(page, size), HttpStatus.OK);
//    }
    @GetMapping("public/product/true")
    public ResponseEntity<List<ProductDTOResponse>>findAllByStatusIsTrue(){
    List<ProductDTOResponse> listPro = productService.findAllByStatus();
    return new ResponseEntity<>(listPro, HttpStatus.OK);
    }

    @GetMapping("public/auth/product/status")
    public ResponseEntity<List<ProductDTOResponse>>findAllProductByStatusIsTrue(
            @RequestParam(defaultValue = "0") int page,
            //Nếu ko cho giá trị page thì mặc định page bằng 0
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy
            //Nếu ko truyền vào tiêu chí tìm thì mặc định tìm theo name
    ){
        List<ProductDTOResponse> listPro = productService.findAllByStatusIsTrue(direction, orderBy, page, size);
        return new ResponseEntity<>(listPro, HttpStatus.OK);
    }

    @GetMapping("public/auth/product/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        ProductDTOResponse pro = productService.findById(id);
        if (pro==null){
            //id ko đúng
            return new ResponseEntity<>(new Message("Id not found"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(pro,HttpStatus.OK);
        }
    }
    @GetMapping("admin/product/{id}")
    public ResponseEntity<?> findByIdByPublic(@PathVariable long id){
        ProductDTOResponse pro = productService.findById(id);
        if (pro==null){
            //id ko đúng
            return new ResponseEntity<>(new Message("Id not found"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(pro,HttpStatus.OK);
        }
    }
    @PostMapping("admin/products")
    public ResponseEntity<ProductDTOResponse>save(@Valid @RequestBody ProductDTORequest productDTORequest){
        ProductDTOResponse pro = productService.save(productDTORequest);
        return new ResponseEntity<>(pro,HttpStatus.CREATED);
    }
    @PutMapping("admin/product/{id}")
    public ResponseEntity<?>update(@RequestBody ProductDTOUpdateRequest productDTOUpdateRequest, @PathVariable long id){
        ProductDTOResponse pro = productService.update(productDTOUpdateRequest,id);
        if(pro==null){
            return new ResponseEntity<>(new Message("Id not found"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(pro,HttpStatus.OK);
        }
    }

    @PatchMapping("admin/product/{id}")
    public ResponseEntity<Message> updateStatus(@PathVariable long id) {
        boolean result = productService.updateStatus(id);
        if (result) {
            return new ResponseEntity<>(new Message("Update status successful"), HttpStatus.OK);
            //Do no content nên sẽ ko hiện ra nội dung gì
        } else {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("admin/products")
    public ResponseEntity<List<ProductDTOResponse>> find(
            @RequestParam(defaultValue = "0") int page,
            //Nếu ko cho giá trị page thì mặc định page bằng 0
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy
            //Nếu ko truyền vào tiêu chí tìm thì mặc định tìm theo name
    ) {
        List<ProductDTOResponse> listPro = productService.find(direction, orderBy, page, size);
        return new ResponseEntity<>(listPro, HttpStatus.OK);
    }

    @GetMapping("admin/product/findNewProduct")
    public ResponseEntity<List<ProductDTOResponse>> findNewProduct(
            @RequestParam(defaultValue = "0") int page,
            //Nếu ko cho giá trị page thì mặc định page bằng 0
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "id") String orderBy
            //Nếu ko truyền vào tiêu chí tìm thì mặc định tìm theo name
    ) {
        List<ProductDTOResponse> listPro = productService.find(direction, orderBy, page, size);
        return new ResponseEntity<>(listPro, HttpStatus.OK);
    }
    //SEARCH theo tên hoặc mô tả
    @GetMapping("public/auth/product/search")
    public ResponseEntity<?> searchByNameOrDescriptions(
            @RequestParam(defaultValue = "") String keySearch)
            {
        return new ResponseEntity<>(productService.searchByNameOrDescriptions(keySearch),HttpStatus.OK);
    }
    //Lấy SP bán chạy
    @GetMapping("public/auth/product/bestSeller")
    public ResponseEntity<?> getAllBestSellerProducts(){
        return new ResponseEntity<>(productService.getBestSellerProducts(),HttpStatus.OK);
    }
    //Lấy SP nổi bật
    @GetMapping("public/auth/product/featuredPr")
    public ResponseEntity<?> getAllFeaturedProducts(){
        return new ResponseEntity<>(productService.getFeaturedProducts(),HttpStatus.OK);
    }
    //Lấy SP mới
    @GetMapping("public/auth/product/top5Products")
    public ResponseEntity<?> get5Products(){
        return new ResponseEntity<>(productService.getTop5Product(),HttpStatus.OK);
    }
}
