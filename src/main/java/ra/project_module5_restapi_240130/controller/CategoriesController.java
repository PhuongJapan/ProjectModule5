package ra.project_module5_restapi_240130.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_module5_restapi_240130.dto.request.CategoriesDTORequest;
import ra.project_module5_restapi_240130.dto.response.CategoriesDTOResponse;
import ra.project_module5_restapi_240130.dto.response.Message;
import ra.project_module5_restapi_240130.model.Categories;
import ra.project_module5_restapi_240130.service.CategoriesService;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;
    @GetMapping("public/auth/categories")
    public ResponseEntity<List<CategoriesDTOResponse>>findAll(){
        List<CategoriesDTOResponse> listCate = categoriesService.findAll();
        return new ResponseEntity<>(listCate, HttpStatus.OK);
    }
    @GetMapping("public/auth/categories/true")
    public ResponseEntity<List<CategoriesDTOResponse>>findAllByStatusIsTrue(){
        List<CategoriesDTOResponse> listCate = categoriesService.findAllByStatus();
        return new ResponseEntity<>(listCate, HttpStatus.OK);
    }
    @GetMapping("public/auth/categories/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        CategoriesDTOResponse categories = categoriesService.findById(id);
        if (categories==null){
            //id ko đúng
            return new ResponseEntity<>(new Message("Id not found"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(categories,HttpStatus.OK);
        }
    }
    @GetMapping("admin/categories/{id}")
    public ResponseEntity<?> findByIdAsPublic(@PathVariable long id){
        CategoriesDTOResponse categories = categoriesService.findById(id);
        if (categories==null){
            //id ko đúng
            return new ResponseEntity<>(new Message("Id not found"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(categories.getListProduct(),HttpStatus.OK);
        }
    }
    @PostMapping("admin/categories")
    public ResponseEntity<CategoriesDTOResponse>save(@Valid @RequestBody CategoriesDTORequest categoriesDTORequest){
        CategoriesDTOResponse categories = categoriesService.save(categoriesDTORequest);
        return new ResponseEntity<>(categories,HttpStatus.CREATED);
    }
    @PutMapping("admin/categories/{id}")
    public ResponseEntity<?>update(@RequestBody CategoriesDTORequest categoriesDTORequest,@PathVariable long id){
        CategoriesDTOResponse categories = categoriesService.update(categoriesDTORequest,id);
        if(categories==null){
            return new ResponseEntity<>(new Message("Id not found"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(categories,HttpStatus.OK);
        }
    }

    @PatchMapping("admin/categories/{id}")
    public ResponseEntity<Message> updateStatus(@PathVariable long id) {
        boolean result = categoriesService.updateStatus(id);
        if (result) {
            return new ResponseEntity<>(new Message("Update status successful"), HttpStatus.OK);
            //Do no content nên sẽ ko hiện ra nội dung gì
        } else {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("admin/categories")
    public ResponseEntity<List<CategoriesDTOResponse>> find(
            @RequestParam(defaultValue = "0") int page,
            //Nếu ko cho giá trị page thì mặc định page bằng 0
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy
            //Nếu ko truyền vào tiêu chí tìm thì mặc định tìm theo name
    ) {
        List<CategoriesDTOResponse> listCategories = categoriesService.find(direction, orderBy, page, size);
        return new ResponseEntity<>(listCategories, HttpStatus.OK);
    }
}
