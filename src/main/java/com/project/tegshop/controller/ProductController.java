package com.project.tegshop.controller;

import com.project.tegshop.dto.ProductDto;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.model.Product;
import com.project.tegshop.service.product.ProductService;
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
public class ProductController {
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAnyAuthority('SELLER')")
    @PostMapping("/product")
    public ResponseEntity<GenericResponse> addNewProductHandler(@Valid @RequestBody ProductDto productDto)
            throws UserException, ProductException {
        Product product = productService.addNewProduct(productDto);
        return new ResponseEntity<>(new GenericResponse(GenericMessage.ADD_NEW_PRODUCT, product), HttpStatus.CREATED);
    }
}
