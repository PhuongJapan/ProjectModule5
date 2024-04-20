package ra.project_module5_restapi_240130.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_module5_restapi_240130.dto.request.AddressDTORequest;
import ra.project_module5_restapi_240130.service.AddressService;

@RestController
@RequestMapping("api/v1/")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @PostMapping("user/address/save")
    public ResponseEntity<?> save(@Valid @RequestBody AddressDTORequest addressDTORequest) {

        return new ResponseEntity<>(addressService.save(addressDTORequest), HttpStatus.CREATED);
    }
    @GetMapping("user/address/{id}/user")
    public ResponseEntity<?> findAllByUserId(@PathVariable long id){
        //Khai báo ở @PathVariable id phải giống trên đường dẫn là id
        return new ResponseEntity<>(addressService.findAllByUserId(id),HttpStatus.OK);
    }
    @GetMapping("user/address/{addressId}")
    public ResponseEntity<?> findByAddressId(@PathVariable long addressId){
        return new ResponseEntity<>(addressService.findByAddressId(addressId),HttpStatus.OK);
    }
}
