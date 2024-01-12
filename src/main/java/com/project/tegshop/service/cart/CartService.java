package com.project.tegshop.service.cart;

import com.project.tegshop.dto.CartItemDto;
import com.project.tegshop.exception.*;
import com.project.tegshop.model.Cart;
import com.project.tegshop.model.CartItem;

public interface CartService {
    CartItem addItemToCart(CartItemDto cartItemDto)
            throws UserNotFoundException, CartItemException, ProductNotFoundException, ProductException;

    Cart getCart() throws UserNotFoundException;

    String deleteItem(Integer id) throws UserNotFoundException, CartItemException, CartItemNotFoundException;

    String clearItem() throws UserNotFoundException, CartItemException;
}
