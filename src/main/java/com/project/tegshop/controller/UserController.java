package com.project.tegshop.controller;

import com.project.tegshop.dto.AddressDto;
import com.project.tegshop.exception.AddressNotFoundException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.Address;
import com.project.tegshop.service.user.UserService;
import com.project.tegshop.shared.GenericMessage;
import com.project.tegshop.shared.GenericResponse;
import com.project.tegshop.shared.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/register/seller")
    public ResponseEntity<MessageResponse> registerSellerHandler() throws UserNotFoundException {
        String message = userService.registerSeller();

        return new ResponseEntity(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @PostMapping("/user/address")
    public ResponseEntity<?> addAddressHandler(@Valid @RequestBody AddressDto addressDto)
            throws UserNotFoundException {
        Address address = userService.addAddress(addressDto);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ADDRESS_ADD, address), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @GetMapping("/user/address")
    public ResponseEntity<?> getAddressHandler() throws UserNotFoundException {
        List<Address> addressList = userService.getAddress();

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ADDRESS_GET, addressList), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @PutMapping("/user/address/{id}")
    public ResponseEntity<?> updateAddressHandler(@PathVariable("id") Integer addressId,
                                                  @Valid @RequestBody AddressDto addressDto)
            throws UserNotFoundException, AddressNotFoundException {
        AddressDto address = userService.updateAddress(addressId, addressDto);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ADDRESS_UPDATE, address), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @DeleteMapping("/user/address/{id}")
    public ResponseEntity<?> deleteAddressHandler(@PathVariable("id") Integer addressId)
            throws UserNotFoundException, AddressNotFoundException {
        String message = userService.deleteAddress(addressId);
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/user/{id}/add-cart")
    public ResponseEntity<?> addCartToOldUser(@PathVariable("id") Integer id) throws UserNotFoundException {
        String message = userService.addCartToOldUser(id);
        return ResponseEntity.ok(new MessageResponse("Add oke"));
    }
}
