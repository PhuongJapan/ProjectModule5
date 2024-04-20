package ra.project_module5_restapi_240130.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_module5_restapi_240130.dto.request.AddressDTORequest;
import ra.project_module5_restapi_240130.model.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findAllByUserId(long userId);
}
