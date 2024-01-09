package com.project.tegshop.service.product;

import com.project.tegshop.dto.ProductDto;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.model.Product;
import com.project.tegshop.model.Status;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.repository.ProductRepository;
import com.project.tegshop.repository.UserRepository;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Product addNewProduct(ProductDto productDto) throws ProductException, UserException {
        String userEmail = getCurrentUser();
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

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
