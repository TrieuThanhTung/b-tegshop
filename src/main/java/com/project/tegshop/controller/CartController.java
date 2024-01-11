package com.project.tegshop.controller;

import com.project.tegshop.dto.CartItemDto;
import com.project.tegshop.exception.CartItemException;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.ProductNotFoundException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.Cart;
import com.project.tegshop.model.CartItem;
import com.project.tegshop.service.cart.CartService;
import com.project.tegshop.shared.GenericMessage;
import com.project.tegshop.shared.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @PostMapping("/cart/add")
    public ResponseEntity<GenericResponse> addItemToCartHandler(@Valid @RequestBody CartItemDto cartItemDto)
            throws UserNotFoundException, CartItemException, ProductNotFoundException, ProductException {
        CartItem cartItem = cartService.addItemToCart(cartItemDto);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ADD_ITEM_TO_CART, cartItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @GetMapping("/cart")
    public ResponseEntity<?> getCartHandler() throws UserNotFoundException {
        Cart cart = cartService.getCart();
        return new ResponseEntity<>(new GenericResponse(GenericMessage.GET_CART, cart), HttpStatus.OK);
    }
}
