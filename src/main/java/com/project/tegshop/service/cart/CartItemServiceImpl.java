package com.project.tegshop.service.cart;

import com.project.tegshop.dto.CartItemDto;
import com.project.tegshop.exception.CartItemException;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.ProductNotFoundException;
import com.project.tegshop.model.CartItem;
import com.project.tegshop.model.Product;
import com.project.tegshop.model.Status;
import com.project.tegshop.repository.ProductRepository;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartItem createCartItem(CartItemDto cartItemDto)
            throws ProductNotFoundException, ProductException, CartItemException {
        Product currentProduct = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));

        if(currentProduct.getQuantity() == 0 || currentProduct.getStatus().equals(Status.OUTOFSTOCK)) {
            throw new ProductException(GenericMessage.PRODUCT_OUT_OF_STOCK);
        }

        if(cartItemDto.getQuantity() > currentProduct.getQuantity()) {
            throw new CartItemException(GenericMessage.ORDER_QUANTITY_EXCEPTION);
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(currentProduct);
        cartItem.setQuantity(cartItemDto.getQuantity());

        return cartItem;
    }
}
