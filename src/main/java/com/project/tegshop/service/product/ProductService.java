package com.project.tegshop.service.product;

import com.project.tegshop.dto.ProductDto;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.ProductNotFoundException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.model.Category;
import com.project.tegshop.model.Product;

import java.util.List;

public interface ProductService {
    Product addNewProduct(ProductDto productDto) throws ProductException, UserException;

    List<Product> getAllProducts();

    Product getProductById(Integer id) throws ProductNotFoundException;

    List<Product> getProductsByCategory(Category category) throws ProductNotFoundException;

    List<Product> getProductBySellerId(Integer id) throws ProductNotFoundException;

    Product updateProduct(ProductDto productDto);
}
