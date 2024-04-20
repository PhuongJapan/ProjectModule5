package ra.project_module5_restapi_240130.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.CategoriesDTORequest;
import ra.project_module5_restapi_240130.dto.response.CategoriesDTOResponse;
import ra.project_module5_restapi_240130.mapper.CategoriesMapper;
import ra.project_module5_restapi_240130.model.Categories;
import ra.project_module5_restapi_240130.repository.CategoriesRepository;
import ra.project_module5_restapi_240130.repository.RolesRepository;
import ra.project_module5_restapi_240130.repository.UserRepository;
import ra.project_module5_restapi_240130.security.jwt.JwtProvider;
import ra.project_module5_restapi_240130.service.CategoriesService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriesServiceImp implements CategoriesService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private CategoriesMapper categoriesMapper;

    @Override
    public List<CategoriesDTOResponse> findAll() {
        List<Categories> listCate = categoriesRepository.findAll();
        return listCate.stream()
                .map(categories -> categoriesMapper.mapperEntityToResponse(categories))
                .collect(Collectors.toList());
    }
    @Override
    public List<CategoriesDTOResponse> findAllByStatus() {
        List<Categories> listCate = categoriesRepository.findAllByStatusIsTrue();
        return listCate.stream()
                .map(categories -> categoriesMapper.mapperEntityToResponse(categories))
                .collect(Collectors.toList());
    }

    @Override
    public CategoriesDTOResponse findById(long id) {
        Optional<Categories> optionalCate= categoriesRepository.findById(id);
        if (optionalCate.isPresent()){
            return categoriesMapper.mapperEntityToResponse(optionalCate.get());
        }
        return null;
    }

    @Override
    public CategoriesDTOResponse save(CategoriesDTORequest categoriesDTORequest) {
        Categories cate = categoriesRepository.save(categoriesMapper.mapperRequestToEntity(categoriesDTORequest));
        return categoriesMapper.mapperEntityToResponse(cate);
    }

    @Override
    public CategoriesDTOResponse update(CategoriesDTORequest categoriesDTORequest, long id) {
        boolean checkExist = categoriesRepository.existsById(id);
        //Tồn tại thì cập nhật
        if(checkExist){
            Categories cate = categoriesMapper.mapperRequestToEntity(categoriesDTORequest);
            //Do categoriesDTORequest ko có ID nên phải chuyển đổi setID
            cate.setId(id);
            Categories cateUpdate = categoriesRepository.save(cate);
            return categoriesMapper.mapperEntityToResponse(cateUpdate);
        }
        return null;
    }

    @Override
    public boolean updateStatus(long id) {
        boolean checkExist = categoriesRepository.existsById(id);
        if(checkExist){
            Optional<Categories> cate = categoriesRepository.findById(id);
            cate.get().setStatus(!cate.get().isStatus());
            categoriesRepository.save(cate.get());
            return true;
        }
        return false;
    }

    @Override
    public List<CategoriesDTOResponse> find(String direction, String orderBy, int page, int size) {
        PageRequest pageable;
        if(direction.equals("ASC")){
            pageable= PageRequest.of(page,size, Sort.by(orderBy).ascending());
        }else {
            pageable = PageRequest.of(page,size,Sort.by(orderBy).descending());
        }
        List<Categories> listCate = categoriesRepository.findAll(pageable).getContent();
        return listCate.stream()
                .map(categories -> categoriesMapper.mapperEntityToResponse(categories))
                .collect(Collectors.toList());
    }
}
