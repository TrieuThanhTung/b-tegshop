package com.project.tegshop.service.order;

import com.project.tegshop.dto.OrderDto;
import com.project.tegshop.exception.AddressNotFoundException;
import com.project.tegshop.exception.CartItemException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.Order;

public interface OrderService {
    Order addNewOrder(OrderDto orderDto) throws UserNotFoundException, AddressNotFoundException, CartItemException;
}
