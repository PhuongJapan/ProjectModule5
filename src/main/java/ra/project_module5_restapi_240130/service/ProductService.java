package ra.project_module5_restapi_240130.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.CategoriesDTORequest;
import ra.project_module5_restapi_240130.dto.request.ProductDTORequest;
import ra.project_module5_restapi_240130.dto.request.ProductDTOUpdateRequest;
import ra.project_module5_restapi_240130.dto.response.CategoriesDTOResponse;
import ra.project_module5_restapi_240130.dto.response.ProductDTOResponse;
import ra.project_module5_restapi_240130.dto.response.ProductDTOResponseForGetTop5Products;
import ra.project_module5_restapi_240130.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ProductService {
    List<ProductDTOResponse> findAll();

    ProductDTOResponse findById(long id);

    ProductDTOResponse save(ProductDTORequest productDTORequest);

    ProductDTOResponse update(ProductDTOUpdateRequest productDTOUpdateRequest, long id);
    List<ProductDTOResponse> searchByNameOrDescriptions(String keySearch);

    List<ProductDTOResponse> findAllByStatus();
    boolean updateStatus(long id);
    List<ProductDTOResponse> find(String direction, String orderBy, int page, int size);

    List<ProductDTOResponse> findAllByStatusIsTrue(String direction, String orderBy, int page, int size);
    List<ProductDTOResponse> getBestSellerProducts();
    List<ProductDTOResponse> getFeaturedProducts();

    List<ProductDTOResponse> getTop5Product();
}
