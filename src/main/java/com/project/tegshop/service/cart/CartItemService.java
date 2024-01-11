package com.project.tegshop.service.cart;

import com.project.tegshop.dto.CartItemDto;
import com.project.tegshop.exception.CartItemException;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.ProductNotFoundException;
import com.project.tegshop.model.CartItem;

public interface CartItemService {
    CartItem createCartItem(CartItemDto cartItemDto) throws ProductNotFoundException, ProductException, CartItemException;
}
