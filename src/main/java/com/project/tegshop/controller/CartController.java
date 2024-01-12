package com.project.tegshop.controller;

import com.project.tegshop.dto.CartItemDto;
import com.project.tegshop.exception.*;
import com.project.tegshop.model.Cart;
import com.project.tegshop.model.CartItem;
import com.project.tegshop.service.cart.CartService;
import com.project.tegshop.shared.GenericMessage;
import com.project.tegshop.shared.GenericResponse;
import com.project.tegshop.shared.MessageResponse;
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

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ADD_ITEM_TO_CART, cartItem), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @GetMapping("/cart")
    public ResponseEntity<?> getCartHandler() throws UserNotFoundException {
        Cart cart = cartService.getCart();
        return new ResponseEntity<>(new GenericResponse(GenericMessage.GET_CART, cart), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @DeleteMapping("/cart/cart-item/{id}")
    public ResponseEntity<?> deleteItemHandler(@PathVariable("id") Integer id)
            throws UserNotFoundException, CartItemException, CartItemNotFoundException {
        String message = cartService.deleteItem(id);

        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @DeleteMapping("/cart/clear")
    public ResponseEntity<?> clearItemFromCart() throws UserNotFoundException, CartItemException {
        String message = cartService.clearItem();
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }
}
