# Back-end project: TegShop
- Project Tegshop is ecommerce website, mainly sells electronic.
- This API performs all the fundamental CRUD operations of any e-commerce platform with user validation at every step.
## Tech stack
- Java
- Spring framework
- Spring boot
- Spring data JPA
- Spring security + JWT
- MySQL

## Module
- Authentication module
- User module
- Address module
- Product module
- Cart module
- Order module

## ER diagram
![image](https://github.com/TrieuThanhTung/b-tegshop/assets/109820897/47c0640a-bf8c-44b6-9130-e44403bcb864)

## Features
- Customer and Seller (ADMIN) authentication & validation with JWT token having 1 hour for security purposes.
- Seller (admin) features:
  - Only registered with valid token can add/upate/delete products from main database.
  - Seller can access the details orders.
- Customers features:
  - Viewing different products and adding them to cart and placing orders.
  - Only logged in user access his orders, cart and other features.
