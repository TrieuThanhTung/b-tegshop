package com.project.tegshop.controller;

import com.project.tegshop.dto.ProductDto;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.ProductNotFoundException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.model.Category;
import com.project.tegshop.model.Product;
import com.project.tegshop.service.product.ProductService;
import com.project.tegshop.shared.GenericMessage;
import com.project.tegshop.shared.GenericResponse;
import com.project.tegshop.shared.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
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

    @GetMapping("/products")
    public ResponseEntity<?> getAllProductsHandler() {
        List<Product> productList = productService.getAllProducts();

        return new ResponseEntity<>(new GenericResponse(GenericMessage.GET_ALL_PRODUCTS, productList), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<GenericResponse> getProductByIdHandler(@PathVariable("id") Integer id)
            throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(new GenericResponse(GenericMessage.GET_PRODUCT_BY_ID, product), HttpStatus.OK);
    }

    @GetMapping("/products/{category}")
    public ResponseEntity<GenericResponse> getProductsByCategoryHandler(@PathVariable("category") Category category,
                                                                        @RequestParam(value = "sort", required = false, defaultValue = "normal") String type,
                                                                        @RequestParam(value = "page", defaultValue = "1") Integer page)
            throws ProductNotFoundException {
        List<Product> productList = productService.getProductsByCategory(category, page, type);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.GET_PRODUCT, productList), HttpStatus.OK);
    }

    @GetMapping("/products/seller")
    public ResponseEntity<GenericResponse> getProductBySellerIdHandler(@RequestParam("id") Integer id)
            throws ProductNotFoundException {
        List<Product> productList = productService.getProductBySellerId(id);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.GET_PRODUCT, productList), HttpStatus.OK);
    }

    @GetMapping("/products/trending")
    public ResponseEntity<GenericResponse> getTrendingProductsHandler() throws ProductNotFoundException {
        List<Product> productList = productService.getTrendingProduct();

        return new ResponseEntity<>(new GenericResponse(GenericMessage.GET_PRODUCT, productList), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SELLER')")
    @PutMapping("/product/{id}")
    public ResponseEntity<GenericResponse> updateProductHandler(@PathVariable("id") Integer id,
                                                                @Valid @RequestBody ProductDto productDto)
            throws UserException, ProductNotFoundException, ProductException {
        Product product = productService.updateProduct(id, productDto);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.PRODUCT_UPDATE, product), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SELLER')")
    @PutMapping("/product/{id}/quantity/{quantity}")
    public ResponseEntity<GenericResponse> updateProductQuantityHandler(@PathVariable("id") Integer id,
                                                                @PathVariable("quantity") Integer quantity)
            throws UserException, ProductNotFoundException, ProductException {
        Product product = productService.updateProductQuantity(id, quantity);

        return new ResponseEntity<>(new GenericResponse(GenericMessage.PRODUCT_UPDATE_QUANTITY, product), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SELLER')")
    @DeleteMapping("/product/{id}")
    public ResponseEntity<MessageResponse> deleteProductByIdHandler(@PathVariable("id") Integer id)
            throws UserException, ProductNotFoundException, ProductException {
        String message = productService.deleteProductById(id);

        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }
}
