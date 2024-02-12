package com.project.tegshop.service.product;

import com.project.tegshop.dto.ProductDto;
import com.project.tegshop.exception.ProductException;
import com.project.tegshop.exception.ProductNotFoundException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.model.Category;
import com.project.tegshop.model.Product;
import com.project.tegshop.model.ProductStatus;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.repository.ProductRepository;
import com.project.tegshop.repository.UserRepository;
import com.project.tegshop.service.user.UserService;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService{
    @Value("${list.product.page}")
    private int pageOffset;
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
                .images(productDto.getImages())
                .user(currentUser)
                .build();

        if(productDto.getQuantity() == 0) {
            product.setStatus(ProductStatus.OUTOFSTOCK);
        } else {
            product.setStatus(ProductStatus.AVAILABLE);
        }

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
    public List<Product> getProductsByCategory(Category category, Integer page, String type) throws ProductNotFoundException {
        List<Product> productList;
         if (type.equalsIgnoreCase("price-asc")) {
            productList = productRepository.getProductByCategoryAndPriceASC(category, (page - 1) * pageOffset)
                    .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));
        } else if (type.equalsIgnoreCase("price-desc")) {
            productList = productRepository.getProductByCategoryAndPriceDESC(category, (page - 1) * pageOffset)
                    .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));
        } else if (type.equalsIgnoreCase("a-z")) {
            productList = productRepository.getProductByCategoryAndProductName(category, (page - 1) * pageOffset)
                    .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));
        } else {
            productList = productRepository.getProductByCategory(category, (page - 1) * pageOffset)
                    .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));
        }
        return productList;
    }

    @Override
    public List<Product> getProductBySellerId(Integer id) throws ProductNotFoundException {
        List<Product> productList = productRepository.findByUserId(id)
                .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));

        return productList;
    }

    @Override
    public List<Product> getTrendingProduct() throws ProductNotFoundException {
        List<Product> productList = productRepository.findByTrending()
                .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));

        return productList;
    }

    @Override
    public Product updateProduct(Integer id, ProductDto productDto)
            throws ProductNotFoundException, UserException, ProductException {
        Product product = preCheckUpdateAndDelete(id);

        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setManufacturer(productDto.getManufacturer());
        product.setQuantity(productDto.getQuantity());
        product.setCategory(productDto.getCategory());
        product.setImages(productDto.getImages());

        if(productDto.getQuantity() == 0) {
            product.setStatus(ProductStatus.OUTOFSTOCK);
        } else {
            product.setStatus(ProductStatus.AVAILABLE);
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProductQuantity(Integer id, Integer quantity) throws ProductNotFoundException, UserException, ProductException {
        if(quantity < 0 || quantity > 100) {
            throw new ProductException(GenericMessage.PRODUCT_QUANTITY_LIMIT);
        }

        Product product = preCheckUpdateAndDelete(id);

        product.setQuantity(quantity);
        if(quantity == 0) {
            product.setStatus(ProductStatus.OUTOFSTOCK);
        } else {
            product.setStatus(ProductStatus.AVAILABLE);
        }

        return productRepository.save(product);
    }

    @Override
    public String deleteProductById(Integer id) throws ProductNotFoundException, UserException, ProductException {
        Product product = preCheckUpdateAndDelete(id);
        productRepository.delete(product);
        return GenericMessage.PRODUCT_DELETE;
    }


    private Product preCheckUpdateAndDelete(Integer id) throws ProductNotFoundException, UserException, ProductException {
        String userEmail = userService.getCurrentUserEmail();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(GenericMessage.PRODUCT_NOT_FOUND));

        UserEntity user = userRepository.findByEmailId(userEmail)
                .orElseThrow(() -> new UserException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        if(!Objects.equals(product.getUser().getUserId(), user.getUserId())) {
            throw new ProductException(GenericMessage.PRODUCT_IS_NOT_YOURS);
        }

        return product;
    }
}
