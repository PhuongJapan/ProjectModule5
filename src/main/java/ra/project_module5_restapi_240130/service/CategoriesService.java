package ra.project_module5_restapi_240130.service;

import ra.project_module5_restapi_240130.dto.request.CategoriesDTORequest;
import ra.project_module5_restapi_240130.dto.response.CategoriesDTOResponse;

import java.util.List;

public interface CategoriesService {
    List<CategoriesDTOResponse> findAll();
    List<CategoriesDTOResponse> findAllByStatus();

    CategoriesDTOResponse findById(long id);

    CategoriesDTOResponse save(CategoriesDTORequest categoriesDTORequest);

    CategoriesDTOResponse update(CategoriesDTORequest categoriesDTORequest, long id);

    boolean updateStatus(long id);

    List<CategoriesDTOResponse> find(String direction, String orderBy, int page, int size);
}
