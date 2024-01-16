package com.project.tegshop.controller;

import com.project.tegshop.dto.AddressDto;
import com.project.tegshop.dto.ChangePasswordDto;
import com.project.tegshop.dto.UpdateProfileDto;
import com.project.tegshop.dto.resetPassword.RequestEmail;
import com.project.tegshop.dto.resetPassword.ResetPasswordDto;
import com.project.tegshop.exception.AddressNotFoundException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.Address;
import com.project.tegshop.model.EmailDetails;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.service.mail.MailService;
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

    private UserService userService;
    private MailService mailService;

    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/register/seller")
    public ResponseEntity<MessageResponse> registerSellerHandler() throws UserNotFoundException {
        String message = userService.registerSeller();

        return new ResponseEntity(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @PutMapping("/user/change-password")
    public ResponseEntity<?> changePasswordHandler(@Valid @RequestBody ChangePasswordDto changePasswordDto)
            throws UserNotFoundException, UserException {
        String message = userService.changePassword(changePasswordDto);

        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @GetMapping("/user/profile")
    public ResponseEntity<?> getProfileHandler()
            throws UserNotFoundException {
        UserEntity user = userService.getProfile();

        return ResponseEntity.ok(new GenericResponse(GenericMessage.USER_GET, user));
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @PutMapping("/user/profile")
    public ResponseEntity<?> updateProfileHandler(@Valid @RequestBody UpdateProfileDto updateProfileDto)
            throws UserNotFoundException {
        String message = userService.updateProfile(updateProfileDto);

        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PostMapping("/user/reset-password/mail")
    public ResponseEntity<?> sendMailResetPasswordHandler(@Valid @RequestBody RequestEmail requestEmail)
            throws Exception {
        String token = userService.sendMailResetPassword(requestEmail);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(requestEmail.getEmailId())
                .subject(GenericMessage.SUBJECT_MAIL_RESET_PASSWORD)
                .message("Token for reset password: " + token)
                .build();
        mailService.sendSimpleMail(emailDetails);

        return ResponseEntity.ok(new MessageResponse(GenericMessage.PASSWORD_SENT_TOKEN));
    }

    @PostMapping("/user/reset-password")
    public ResponseEntity<?> resetPasswordHandler(@Valid @RequestBody ResetPasswordDto resetPasswordDto)
            throws UserException {
        String message = userService.resetPassword(resetPasswordDto);

        return ResponseEntity.ok(new MessageResponse(message));
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

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsersHandler() {
        List<UserEntity> userList = userService.getAllUsers();

        return new ResponseEntity<>(new GenericResponse(GenericMessage.USER_GET, userList), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin/user/{id}")
    public ResponseEntity<?> deleteUserByIdHandler(@PathVariable("id") Integer id) throws UserNotFoundException {
        String message = userService.deleteUserById(id);

        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }
}
