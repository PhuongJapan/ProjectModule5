package ra.project_module5_restapi_240130.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_module5_restapi_240130.model.Categories;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories,Long> {
    List<Categories> findAllByStatusIsTrue();
}
