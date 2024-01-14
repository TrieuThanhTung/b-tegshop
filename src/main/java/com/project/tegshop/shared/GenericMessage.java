package com.project.tegshop.shared;

public interface GenericMessage {
    String EMAIL_ALREADY_IN_USE = "Email already in use!";
    String USER_WITH_GIVEN_EMAIL_NOT_FOUND = "User with given email not found!";
    String PASSWORD_NOT_MATCH = "Password not match";
    String TOKEN_CONFIRM_IS_SENT = "Token confirm is sent to your email." +
            " Please USE it in 30 minutes to confirm registration.";
    String REGISTER_SUCCESSFULLY = "Register successfully";
    String LOGIN_SUCCESSFULLY = "Login successfully";
    String REGISTER_SELLER_SUCCESSFULLY = "Register seller successfully";
    String REGISTRATION_TOKEN_NOT_FOUND = "Registration token not found!";
    String REGISTRATION_TOKEN_IS_EXPIRED = "Registration token is expired";
    String USER_VERIFIED_REGISTRATION = "User verified registration";
    String USER_WAS_NOT_VERIFIED = "User wasn't verified";
    String SUBJECT_MAIL = "Mail verify registration";
    String ERROR_WHILE_SENDING_EMAIL = "Error while sending email";
    String ADD_NEW_PRODUCT = "Add new product successfully";
    String PRODUCT_ALREADY_EXISTS = "Product already exists with given name";
    String GET_ALL_PRODUCTS = "Get all products successfully";
    String GET_PRODUCT = "Get product successfully";
    String GET_PRODUCT_BY_ID = "Get product by id successfully";
    String PRODUCT_NOT_FOUND = "Product not found with given id!";
    String PRODUCT_UPDATE = "Update product successfully";
    String PRODUCT_IS_NOT_YOURS = "Product in not yours!";
    String PRODUCT_UPDATE_QUANTITY = "Update product quantity successfully";
    String PRODUCT_QUANTITY_LIMIT = "Product quantity must be greater or equal to 0 and less or equal to 100!";
    String PRODUCT_DELETE = "Delete product successfully";
    String PRODUCT_OUT_OF_STOCK = "Product out of stock!";
    String ORDER_QUANTITY_EXCEPTION = "Order's quantity is greater than current product quantity";
    String ADD_ITEM_TO_CART = "Add item to cart successfully";
    String GET_CART = "Get cart successfully";
    String CART_EMPTY = "Cart empty!";
    String CART_DELETE_ITEM = "Delete product from cart successfully";
    String CART_ITEM_NOT_ADDED = "Product is not added to cart!";
    String CLEAR_CART = "Delete all items in cart successfully";
    String ADDRESS_ADD = "Add new address successfully";
    String ADDRESS_GET = "Get addresses successfully";
    String ADDRESS_NOT_FOUND = "Address not found!";
    String ADDRESS_UPDATE = "Update address successfully";
    String ADDRESS_DELETE = "Delete address successfully";
    String ORDER_ADD = "Add new order successfully";
    String ORDER_GET = "Get order successfully";
    String ORDER_NOT_FOUND = "Order not found!";
    String ORDER_STATUS_NOT_CHANGE = "Order's status not change!";
    String ORDER_STATUS = "Change order's status successfully";
    String ORDER_CANCEL = "Cancel order successfully";
    String ORDER_CANCELED = "FAIL. Only cancel order when order's status is pending.";
}
