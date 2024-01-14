package com.project.tegshop.service.order;

import com.project.tegshop.dto.OrderDto;
import com.project.tegshop.exception.AddressNotFoundException;
import com.project.tegshop.exception.CartItemException;
import com.project.tegshop.exception.OrderNotFoundException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.*;
import com.project.tegshop.repository.OrderRepository;
import com.project.tegshop.service.cart.CartService;
import com.project.tegshop.service.user.UserService;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;
    private UserService userService;
    private CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserService userService,
                            CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
    public Order addNewOrder(OrderDto orderDto) throws UserNotFoundException, AddressNotFoundException, CartItemException {
        UserEntity currentUser = userService.getCurrentUser();

        Address orderAddress = new Address();

        List<Address> addressList = currentUser.getAddresses();
        boolean isHasAddress = false;
        for(Address address : addressList) {
            if(address.getAddressId() == orderDto.getAddressId()) {
                orderAddress = address;
                isHasAddress = true;
                break;
            }
        }
        if(!isHasAddress) {
            throw new AddressNotFoundException(GenericMessage.ADDRESS_NOT_FOUND);
        }

        Cart cartUser = currentUser.getCart();
        if(cartUser.getCartItemList().size() == 0) {
            throw new CartItemException(GenericMessage.CART_EMPTY);
        }
        Long orderTotal = cartUser.getCartTotal();
        List<CartItem> cartItemOrderList = cartUser.getCartItemList().stream()
                .toList();

        cartService.clearItem();

        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .description(orderDto.getDescription())
                .cartItemList(cartItemOrderList)
                .total(orderTotal)
                .address(orderAddress)
                .user(currentUser)
                .build();

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrder() throws UserNotFoundException, OrderNotFoundException {
        UserEntity currentUser = userService.getCurrentUser();

        return orderRepository.findByUserId(currentUser.getUserId())
                .orElseThrow(() -> new OrderNotFoundException(GenericMessage.ORDER_NOT_FOUND));
    }
}
