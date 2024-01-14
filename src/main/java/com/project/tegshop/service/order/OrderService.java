package com.project.tegshop.service.order;

import com.project.tegshop.dto.OrderDto;
import com.project.tegshop.dto.StatusOrderDto;
import com.project.tegshop.exception.*;
import com.project.tegshop.model.Order;

import java.util.List;

public interface OrderService {
    Order addNewOrder(OrderDto orderDto) throws UserNotFoundException, AddressNotFoundException, CartItemException;

    List<Order> getOrder() throws UserNotFoundException, OrderNotFoundException;

    Order getOrderById(Integer id) throws OrderNotFoundException, UserNotFoundException;

    Order setStatusOrder(StatusOrderDto statusOrderDto) throws OrderNotFoundException, UserNotFoundException, OrderException;

    List<Order> getAllOrder();

    Order cancelOrder(Integer id) throws OrderNotFoundException, UserNotFoundException, OrderException;
}
