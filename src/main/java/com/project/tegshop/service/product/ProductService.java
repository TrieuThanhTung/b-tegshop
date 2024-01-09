package com.project.tegshop.service.product;

import com.project.tegshop.dto.ProductDto;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.model.Product;

public interface ProductService {
    Product addNewProduct(ProductDto productDto) throws ProductException, UserException;
}
