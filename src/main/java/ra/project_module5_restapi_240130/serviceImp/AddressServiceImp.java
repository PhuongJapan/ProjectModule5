package ra.project_module5_restapi_240130.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_module5_restapi_240130.dto.request.AddressDTORequest;
import ra.project_module5_restapi_240130.dto.response.AddressDTOResponse;
import ra.project_module5_restapi_240130.mapper.AddressMapper;
import ra.project_module5_restapi_240130.model.Address;
import ra.project_module5_restapi_240130.repository.AddressRepository;
import ra.project_module5_restapi_240130.repository.UserRepository;
import ra.project_module5_restapi_240130.service.AddressService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImp implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressMapper addressMapper;
    @Override
    public List<AddressDTOResponse> findAllByUserId(long userId) {
        List<Address> listAdd = addressRepository.findAllByUserId(userId);
        return listAdd.stream().map(address -> addressMapper.mapperEntityToResponse(address)).collect(Collectors.toList());

    }
    @Override
    public AddressDTOResponse findByAddressId(long addressId) {
        //Khi tìm ID thì trả về Optional để kiểm tra được null=> ko bị lỗi
        Optional<Address> addressOpt = addressRepository.findById(addressId);
        if(addressOpt.isPresent()){
            return addressMapper.mapperEntityToResponse(addressOpt.get());
        }else {
            throw new RuntimeException("Id not found");
        }
    }
    @Override
    public AddressDTOResponse save(AddressDTORequest addressDTORequest) {
        return addressMapper.mapperEntityToResponse(addressRepository.save(addressMapper.mapperRequestToEntity(addressDTORequest)));
    }
}
