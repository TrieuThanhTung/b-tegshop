package com.project.tegshop.controller;

import com.project.tegshop.dto.OrderDto;
import com.project.tegshop.dto.StatusOrderDto;
import com.project.tegshop.exception.*;
import com.project.tegshop.model.Order;
import com.project.tegshop.service.order.OrderService;
import com.project.tegshop.shared.GenericMessage;
import com.project.tegshop.shared.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @PostMapping("/order")
    public ResponseEntity<?> addNewOrderHandler(@Valid @RequestBody OrderDto orderDto)
            throws UserNotFoundException, AddressNotFoundException, CartItemException {
        Order order = orderService.addNewOrder(orderDto);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ORDER_ADD, order), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @GetMapping("/order")
    public ResponseEntity<?> getOrderHandler() throws UserNotFoundException, OrderNotFoundException {
        List<Order> orderList = orderService.getOrder();

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ORDER_GET, orderList), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderByIdHandler(@PathVariable("id") Integer id)
            throws OrderNotFoundException, UserNotFoundException {
        Order order = orderService.getOrderById(id);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ORDER_GET, order), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @PutMapping("/order")
    public ResponseEntity<?> setStatusOrderHandler(@Valid @RequestBody StatusOrderDto statusOrderDto)
            throws OrderNotFoundException, UserNotFoundException, OrderException {
        Order order = orderService.setStatusOrder(statusOrderDto);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ORDER_GET, order), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/order/all")
    public ResponseEntity<?> getAllOrderHandler() {
        List<Order> orders = orderService.getAllOrder();

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ORDER_GET, orders), HttpStatus.OK);
    }
}
