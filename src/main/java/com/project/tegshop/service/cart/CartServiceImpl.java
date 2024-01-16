package com.project.tegshop.service.cart;

import com.project.tegshop.dto.CartItemDto;
import com.project.tegshop.exception.*;
import com.project.tegshop.model.Cart;
import com.project.tegshop.model.CartItem;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.repository.CartRepository;
import com.project.tegshop.service.user.UserService;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private UserService userService;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemService cartItemService,
                           UserService userService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.userService = userService;
    }

    @Override
    public CartItem addItemToCart(CartItemDto cartItemDto)
            throws UserNotFoundException, CartItemException, ProductNotFoundException, ProductException {
        UserEntity currentUser = userService.getCurrentUser();
        CartItem cartItem = cartItemService.createCartItem(cartItemDto);

        Cart cartUser = currentUser.getCart();
        List<CartItem> cartItemList = cartUser.getCartItemList();

        if(cartItemList.size() == 0) {
            cartItemList.add(cartItem);
            cartUser.setCartTotal((long)cartItem.getQuantity() * cartItem.getProduct().getPrice());
        } else {
            boolean isHasItem = false;
            for(CartItem c : cartItemList) {
                if(c.getProduct().getProductId() == cartItem.getProduct().getProductId()) {
                    c.setQuantity(c.getQuantity() + cartItem.getQuantity());
                    if(c.getQuantity() > cartItem.getProduct().getQuantity()) {
                        throw new CartItemException(GenericMessage.ORDER_QUANTITY_EXCEPTION);
                    }
                    cartUser.setCartTotal(cartUser.getCartTotal() + (long)cartItem.getQuantity() * cartItem.getProduct().getPrice());
                    isHasItem = true;
                    break;
                }
            }

            if(!isHasItem) {
                cartItemList.add(cartItem);
                cartUser.setCartTotal(cartUser.getCartTotal() + (long)cartItem.getQuantity() * cartItem.getProduct().getPrice());
            }
        }

        cartRepository.save(cartUser);

        return cartItem;
    }

    @Override
    public Cart getCart() throws UserNotFoundException {
        UserEntity currentUser = userService.getCurrentUser();

        return currentUser.getCart();
    }

    @Override
    public String deleteItem(Integer id)
            throws UserNotFoundException, CartItemException, CartItemNotFoundException {
        UserEntity currentUser = userService.getCurrentUser();

        Cart cartUser = currentUser.getCart();
        List<CartItem> cartItemList = cartUser.getCartItemList();

        if(cartItemList.size() == 0) {
            throw new CartItemException(GenericMessage.CART_EMPTY);
        }

        boolean isHasItem = false;
        for(CartItem c : cartItemList) {
            if(c.getCartItemId() == id) {
                cartUser.setCartTotal(cartUser.getCartTotal() - (long) c.getQuantity() * c.getProduct().getPrice());
                cartItemList.remove(c);
                cartItemService.deleteCartItem(c);
                isHasItem = true;
                break;
            }
        }

        if(!isHasItem) {
            throw new CartItemNotFoundException(GenericMessage.CART_ITEM_NOT_ADDED);
        }
        cartRepository.save(cartUser);

        return GenericMessage.CART_DELETE_ITEM;
    }

    @Override
    public String clearItem() throws UserNotFoundException, CartItemException {
        UserEntity currentUser = userService.getCurrentUser();

        Cart cartUser = currentUser.getCart();
        List<CartItem> cartItemList = cartUser.getCartItemList();

        if(cartItemList.size() == 0) {
            throw new CartItemException(GenericMessage.CART_EMPTY);
        }

//        for(CartItem c : cartItemList) {
//            cartItemList.remove(c);
//            cartItemService.deleteCartItem(c);
//        }

        cartItemList.clear();
        cartUser.setCartTotal(0L);

        cartRepository.save(cartUser);

        return GenericMessage.CLEAR_CART;
    }
}
