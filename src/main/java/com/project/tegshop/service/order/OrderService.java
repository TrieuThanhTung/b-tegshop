package com.project.tegshop.service.order;

import com.project.tegshop.dto.OrderDto;
import com.project.tegshop.exception.AddressNotFoundException;
import com.project.tegshop.exception.CartItemException;
import com.project.tegshop.exception.OrderNotFoundException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.Order;

import java.util.List;

public interface OrderService {
    Order addNewOrder(OrderDto orderDto) throws UserNotFoundException, AddressNotFoundException, CartItemException;

    List<Order> getOrder() throws UserNotFoundException, OrderNotFoundException;
}
