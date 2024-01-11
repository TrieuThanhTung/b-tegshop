package com.project.tegshop.controller;

import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.service.user.UserService;
import com.project.tegshop.shared.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/register/seller")
    public ResponseEntity<MessageResponse> registerSellerHandler() throws UserNotFoundException {
        String message = userService.registerSeller();

        return new ResponseEntity(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/user/{id}/add-cart")
    public ResponseEntity<?> addCartToOldUser(@PathVariable("id") Integer id) throws UserNotFoundException {
        String message = userService.addCartToOldUser(id);
        return ResponseEntity.ok(new MessageResponse("Add oke"));
    }
}
