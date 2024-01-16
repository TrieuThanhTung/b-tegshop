package com.project.tegshop.service.order;

import com.project.tegshop.dto.OrderDto;
import com.project.tegshop.dto.StatusOrderDto;
import com.project.tegshop.exception.*;
import com.project.tegshop.model.*;
import com.project.tegshop.repository.OrderRepository;
import com.project.tegshop.service.cart.CartService;
import com.project.tegshop.service.user.UserService;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public Order getOrderById(Integer id) throws OrderNotFoundException, UserNotFoundException {
        UserEntity currentUser = userService.getCurrentUser();

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(GenericMessage.ORDER_NOT_FOUND));

        if(!Objects.equals(order.getUser().getUserId(), currentUser.getUserId())) {
            throw new OrderNotFoundException(GenericMessage.ORDER_NOT_FOUND);
        }

        return order;
    }

    @Override
    public Order setStatusOrder(StatusOrderDto statusOrderDto)
            throws OrderNotFoundException, UserNotFoundException, OrderException {
        Order order = getOrderById(statusOrderDto.getId());

        if(order.getOrderStatus() == statusOrderDto.getStatus()) {
            throw new OrderException(GenericMessage.ORDER_STATUS_NOT_CHANGE);
        }

        order.setOrderStatus(statusOrderDto.getStatus());
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order cancelOrder(Integer id) throws OrderNotFoundException, UserNotFoundException, OrderException {
        Order order = getOrderById(id);

        if(order.getOrderStatus() != OrderStatus.PENDING) {
            throw new OrderException(GenericMessage.ORDER_CANCELED);
        }

        order.setOrderStatus(OrderStatus.CANCELED);

        orderRepository.save(order);

        return order;
    }

    @Override
    public List<Order> getAllOrder() {
        List<Order> orders = orderRepository.findAll();

        return orders;
    }

}
