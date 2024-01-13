package com.project.tegshop.controller;

import com.project.tegshop.dto.OrderDto;
import com.project.tegshop.exception.AddressNotFoundException;
import com.project.tegshop.exception.CartItemException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.Order;
import com.project.tegshop.service.order.OrderService;
import com.project.tegshop.shared.GenericMessage;
import com.project.tegshop.shared.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'SELLER')")
    @PostMapping("/order/add")
    public ResponseEntity<?> addNewOrderHandler(@Valid @RequestBody OrderDto orderDto)
            throws UserNotFoundException, AddressNotFoundException, CartItemException {
        Order order = orderService.addNewOrder(orderDto);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.ORDER_ADD, order), HttpStatus.CREATED);
    }
}
