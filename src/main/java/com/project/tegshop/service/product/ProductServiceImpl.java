package com.project.tegshop.service.product;

import com.project.tegshop.dto.ProductDto;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.ProductNotFoundException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.model.Category;
import com.project.tegshop.model.Product;
import com.project.tegshop.model.Status;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.repository.ProductRepository;
import com.project.tegshop.repository.UserRepository;
import com.project.tegshop.service.user.UserService;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Product addNewProduct(ProductDto productDto) throws ProductException, UserException {
        String userEmail = userService.getCurrentUserEmail();
        UserEntity currentUser = userRepository.findByEmailId(userEmail)
                .orElseThrow(() -> new UserException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        if(productRepository.findByProductName(productDto.getProductName()).isPresent()) {
            throw new ProductException(GenericMessage.PRODUCT_ALREADY_EXISTS);
        }

        Product product = Product.builder()
                .productName(productDto.getProductName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .manufacturer(productDto.getManufacturer())
                .quantity(productDto.getQuantity())
                .category(productDto.getCategory())
                .status(Status.AVAILABLE)
                .images(productDto.getImages())
                .user(currentUser)
                .build();

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList;
    }

    @Override
    public Product getProductById(Integer id) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));

        return product;
    }

    @Override
    public List<Product> getProductsByCategory(Category category) throws ProductNotFoundException {
        List<Product> productList = productRepository.findByCategory(category)
                .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));

        return productList;
    }

    @Override
    public List<Product> getProductBySellerId(Integer id) throws ProductNotFoundException {
        List<Product> productList = productRepository.findByUserId(id)
                .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));

        return productList;
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        return null;
    }


}
