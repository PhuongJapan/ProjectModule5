package ra.project_module5_restapi_240130.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.project_module5_restapi_240130.model.ERoles;
import ra.project_module5_restapi_240130.model.Product;
import ra.project_module5_restapi_240130.model.Roles;
import ra.project_module5_restapi_240130.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserNameAndStatus(String userName, boolean status);
//    List<User> findAllByListRole(ERoles eRoles);
//    Optional<Roles> findAllByListRole(ERoles eRoles);

//    @Query("select u from User u where u.fullName like %:name%")
//    Page<User> searchAllByFullNameOrUserName(String name, Pageable pageable);
    List<User> findAllByFullNameContainingIgnoreCase(String fullName);

}
