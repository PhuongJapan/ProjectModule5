package ra.project_module5_restapi_240130.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.ProductDTORequest;
import ra.project_module5_restapi_240130.dto.request.ProductDTOUpdateRequest;
import ra.project_module5_restapi_240130.dto.response.CategoriesDTOResponse;
import ra.project_module5_restapi_240130.dto.response.ProductDTOResponse;
import ra.project_module5_restapi_240130.dto.response.ProductDTOResponseForGetTop5Products;
import ra.project_module5_restapi_240130.mapper.CategoriesMapper;
import ra.project_module5_restapi_240130.mapper.ProductMapper;
import ra.project_module5_restapi_240130.model.Categories;
import ra.project_module5_restapi_240130.model.OrderDetail;
import ra.project_module5_restapi_240130.model.Product;
import ra.project_module5_restapi_240130.repository.CategoriesRepository;
import ra.project_module5_restapi_240130.repository.OrderDetailRepository;
import ra.project_module5_restapi_240130.repository.ProductRepository;
import ra.project_module5_restapi_240130.repository.WishListRepository;
import ra.project_module5_restapi_240130.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoriesMapper categoriesMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Override
    public List<ProductDTOResponse> findAll() {
        List<Product> listPro = productRepository.findAll();
        return listPro.stream()
                .map(product -> productMapper.mapperEntityToResponse(product))
                .collect(Collectors.toList());
    }
//    public Map<String, Object> findAll(int page, int size) {
//        //Tạo 1 đối tượng Pageable để phân trang
//
//        //Gọi findAll của JpaRepository để lấy các sản phẩm có phân trang
//        Page<Product> pageProduct = productRepository.findAll(pageable);
//        //Lấy tổng số trang trong kết quả
//        int totalPage = pageProduct.getTotalPages();
//        //Lấy dữ liệu sản phẩm trên 1 trang
//        List<Product> listProduct = pageProduct.getContent();
//        //Chuyển từ listProduct sang listProductDTOResponse
//        List<ProductDTOResponse> listProductDTOResponse = listProduct.stream()
//                .map(product -> productMapper.mapperEntityToResponse(product))
//                .collect(Collectors.toList());
//        //Khởi tạo Map để trả về cho client
//        Map<String, Object> data = new HashMap<>();
//        //tổng số trang khi phân trang
//        data.put("totalPage", totalPage);
//        //Dữ liệu trên 1 trang
//        data.put("products", listProductDTOResponse);
//        return data;
//    }

    @Override
    public ProductDTOResponse findById(long id) {
        Optional<Product> optionalPro= productRepository.findById(id);
        if (optionalPro.isPresent()){
            return productMapper.mapperEntityToResponse(optionalPro.get());
        }
        return null;
    }

    @Override
    public ProductDTOResponse save(ProductDTORequest productDTORequest) {
        Product pro = productRepository.save(productMapper.mapperRequestToEntity(productDTORequest));
        return productMapper.mapperEntityToResponse(pro);
    }

    @Override
    public List<ProductDTOResponse> getTop5Product() {
        List<Product> listProduct= productRepository.findTop5ByOrderByCreatedAtDesc();
        List<ProductDTOResponse> dtoResponseList= listProduct.stream()
                .map(product -> productMapper.mapperEntityToResponse(product)).collect(Collectors.toList());
        return dtoResponseList;
    }

    @Override
    public ProductDTOResponse update(ProductDTOUpdateRequest productDTOUpdateRequest, long id) {
        boolean checkExist = productRepository.existsById(id);
        //Tồn tại thì cập nhật
        if(checkExist){
            Product pro = productRepository.findById(id).get();
            //Do categoriesDTORequest ko có ID nên phải chuyển đổi setID
            pro.setId(id);
            pro.setName(productDTOUpdateRequest.getName());
            pro.setDescriptions(productDTOUpdateRequest.getDescriptions());
            pro.setUnit_price(productDTOUpdateRequest.getUnit_price());
            pro.setCatalog(categoriesRepository.findById(productDTOUpdateRequest.getCatalogId()).orElseThrow(()->new RuntimeException("catalog id not found")));
            pro.setStock_quantity(productDTOUpdateRequest.getStock_quantity());
            pro.setStatus(productDTOUpdateRequest.isStatus());
            Product proUpdate = productRepository.save(pro);
            return productMapper.mapperEntityToResponse(proUpdate);
        }
        return null;
    }

    @Override
    public List<ProductDTOResponse> searchByNameOrDescriptions(String keySearch) {
        List<Product> products = productRepository.findAllByNameContainingIgnoreCaseOrDescriptionsContainingIgnoreCase(keySearch,keySearch);
        return products.stream().map(product -> productMapper.mapperEntityToResponse(product)).collect(Collectors.toList());
    }
    // Lấy danh sách sản phẩm đang bán và có sắp xếp phân trang
//    @Override
//    public Map<String, Object> findAllProductAndOrder(int page, int size,String name) {
//        Pageable pageablemm = PageRequest.of(page, size);
//        Page<Product> pageProduct = productRepository.findAllByStatusIsTrueAndName(name,pageable);
//        //Lấy tổng số trang trong kết quả
//        int totalPage = pageProduct.getTotalPages();
//        //Lấy dữ liệu sản phẩm trên 1 trang
//        List<Product> listProduct = pageProduct.getContent();
//        //Chuyển từ listProduct sang listProductDTOResponse
//        List<ProductDTOResponse> listProductDTOResponse = listProduct.stream()
//                .map(product -> productMapper.mapperEntityToResponse(product))
//                .collect(Collectors.toList());
//        //Khởi tạo Map để trả về cho client
//        Map<String, Object> data = new HashMap<>();
//        //tổng số trang khi phân trang
//        data.put("totalPage", totalPage);
//        //Dữ liệu trên 1 trang
//        data.put("product", listProductDTOResponse);
//        return data;
//    }

    @Override
    public boolean updateStatus(long id) {
        boolean checkExist = productRepository.existsById(id);
        if(checkExist){
            Optional<Product> pro = productRepository.findById(id);
            pro.get().setStatus(!pro.get().isStatus());
            productRepository.save(pro.get());
            return true;
        }
        return false;
    }
    @Override
    public List<ProductDTOResponse> findAllByStatus() {
        List<Product> listPro = productRepository.findAllByStatusIsTrue();
        return listPro.stream()
                .map(product -> productMapper.mapperEntityToResponse(product))
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductDTOResponse> find(String direction, String orderBy, int page, int size) {
        PageRequest pageable;
        if(direction.equals("ASC")){
            pageable= PageRequest.of(page,size, Sort.by(orderBy).ascending());
        }else {
            pageable = PageRequest.of(page,size,Sort.by(orderBy).descending());
        }
        List<Product> listPro = productRepository.findAll(pageable).getContent();
        return listPro.stream()
                .map(product -> productMapper.mapperEntityToResponse(product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTOResponse> getFeaturedProducts() {
        List<Product> productList = wishListRepository.getAllFeaturedProducts();
        return productList.stream()
                .map(product -> productMapper.mapperEntityToResponse(product)).collect(Collectors.toList());

    }

    @Override
    public List<ProductDTOResponse> getBestSellerProducts() {
        List<Product> productList = orderDetailRepository.getAllByBestSellerProducts();
        return productList.stream()
                .map(product ->productMapper.mapperEntityToResponse(product)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTOResponse> findAllByStatusIsTrue(String direction, String orderBy, int page, int size) {
        PageRequest pageable;
        if(direction.equals("ASC")){
            pageable= PageRequest.of(page,size, Sort.by(orderBy).ascending());
        }else {
            pageable = PageRequest.of(page,size,Sort.by(orderBy).descending());
        }
        List<Product> listPro = productRepository.findAllByStatusIsTrue(pageable).getContent();
        return listPro.stream()
                .map(product -> productMapper.mapperEntityToResponse(product))
                .collect(Collectors.toList());
    }
}
