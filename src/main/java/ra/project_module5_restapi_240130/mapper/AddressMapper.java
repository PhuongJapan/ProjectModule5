package ra.project_module5_restapi_240130.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.project_module5_restapi_240130.dto.request.AddressDTORequest;
import ra.project_module5_restapi_240130.dto.request.CategoriesDTORequest;
import ra.project_module5_restapi_240130.dto.response.AddressDTOResponse;
import ra.project_module5_restapi_240130.dto.response.CategoriesDTOResponse;
import ra.project_module5_restapi_240130.model.Address;
import ra.project_module5_restapi_240130.model.Categories;
import ra.project_module5_restapi_240130.repository.UserRepository;

@Component
public class AddressMapper implements GenericMapper<Address, AddressDTORequest, AddressDTOResponse>{
    @Autowired
    private UserRepository userRepository;
    @Override
    public Address mapperRequestToEntity(AddressDTORequest addressDTORequest) {
        return Address.builder()
                .full_address(addressDTORequest.getFullAddress())
                .phone(addressDTORequest.getPhone())
                .receive_name(addressDTORequest.getReceiveName())
                .user(userRepository.findById(addressDTORequest.getUserId()).get()).build();
    }

    @Override
    public AddressDTOResponse mapperEntityToResponse(Address address) {
        return new AddressDTOResponse(
                address.getId(),
                address.getFull_address(),
                address.getPhone(),
                address.getReceive_name(),
                address.getUser().getId(),
                address.getUser().getUserName()

        );
    }
}
