package com.project.tegshop.service.cart;

import com.project.tegshop.dto.CartItemDto;
import com.project.tegshop.exception.CartItemException;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.ProductNotFoundException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.Cart;
import com.project.tegshop.model.CartItem;

public interface CartService {
    CartItem addItemToCart(CartItemDto cartItemDto)
            throws UserNotFoundException, CartItemException, ProductNotFoundException, ProductException;

    Cart getCart() throws UserNotFoundException;
}
