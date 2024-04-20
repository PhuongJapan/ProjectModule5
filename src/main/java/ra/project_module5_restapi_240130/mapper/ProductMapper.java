package ra.project_module5_restapi_240130.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.project_module5_restapi_240130.dto.request.ProductDTORequest;
import ra.project_module5_restapi_240130.dto.response.ProductDTOResponse;
import ra.project_module5_restapi_240130.model.Categories;
import ra.project_module5_restapi_240130.model.Product;
import ra.project_module5_restapi_240130.repository.CategoriesRepository;

@Component
public class ProductMapper implements GenericMapper<Product, ProductDTORequest,ProductDTOResponse> {
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Override
    public Product mapperRequestToEntity(ProductDTORequest productDTORequest) {
        return Product.builder()
                .name(productDTORequest.getName())
                .descriptions(productDTORequest.getDescriptions())
                .unit_price(productDTORequest.getUnit_price())
                .stock_quantity(productDTORequest.getStock_quantity())
                .image(productDTORequest.getImage())
                .status(true)
                .catalog(categoriesRepository.findById(productDTORequest.getCatalogId()).orElse(null)).build();
    }

    @Override
    public ProductDTOResponse mapperEntityToResponse(Product product) {
        return new ProductDTOResponse(
                product.getId(),
                product.getName(),
                product.getDescriptions(),
                product.getUnit_price(),
                product.getImage(),
                product.getStock_quantity(),
                product.getCatalog().getId(),
                product.getCatalog().getName(),
                product.isStatus()
        );
    }
}
