package ra.project_module5_restapi_240130.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.project_module5_restapi_240130.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByStatusIsTrue();
    Page<Product> findAllByStatusIsTrue(Pageable pageable);
//    @Query("select p from Product p where p.name like %:name% or p.descriptions like %:name%")
//    Page<Product>searchByNameOrDescriptions(String name, Pageable pageable);
    List<Product> findAllByNameContainingIgnoreCaseOrDescriptionsContainingIgnoreCase(String keySearch, String name);

//    Page<Product> findAllByNameContainingIgnoreCaseOrDescriptionsContainingIgnoreCase(String productName, String descriptions);
    Page<Product> findAllByStatusIsTrueAndName(String name, Pageable pageable);
//    List<Product> findTop5ByOrderByCreated_atDesc();
    @Query("SELECT p FROM Product p ORDER BY p.created_at DESC")
    List<Product> findTop5ByOrderByCreatedAtDesc();
}
