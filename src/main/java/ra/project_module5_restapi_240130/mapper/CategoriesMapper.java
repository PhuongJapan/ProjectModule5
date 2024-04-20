package ra.project_module5_restapi_240130.mapper;

import org.springframework.stereotype.Component;
import ra.project_module5_restapi_240130.dto.request.CategoriesDTORequest;
import ra.project_module5_restapi_240130.dto.response.CategoriesDTOResponse;
import ra.project_module5_restapi_240130.model.Categories;

@Component
public class CategoriesMapper implements GenericMapper<Categories, CategoriesDTORequest, CategoriesDTOResponse>{
    @Override
    public Categories mapperRequestToEntity(CategoriesDTORequest categoriesDTORequest) {
        return Categories.builder()
                .name(categoriesDTORequest.getName())
                .descriptions(categoriesDTORequest.getDescriptions())
                .status(true).build();
    }

    @Override
    public CategoriesDTOResponse mapperEntityToResponse(Categories categories) {
        return new CategoriesDTOResponse(
                categories.getId(),
                categories.getName(),
                categories.getDescriptions(),
                categories.isStatus(),
                categories.getListProduct()

        );
    }
}
