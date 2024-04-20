package ra.project_module5_restapi_240130.service;

import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.AddressDTORequest;
import ra.project_module5_restapi_240130.dto.response.AddressDTOResponse;

import java.util.List;

@Service
public interface AddressService {
    List<AddressDTOResponse> findAllByUserId(long userId);
    AddressDTOResponse findByAddressId(long addressId);
    AddressDTOResponse save(AddressDTORequest addressDTORequest);
}
