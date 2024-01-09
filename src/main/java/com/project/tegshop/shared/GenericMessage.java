package com.project.tegshop.shared;

public interface GenericMessage {
    String EMAIL_ALREADY_IN_USE = "Email already in use!";
    String USER_WITH_GIVEN_EMAIL_NOT_FOUND = "User with given email not found!";
    String PASSWORD_NOT_MATCH = "Password not match";
    String LINK_CONFIRM_IS_SENT = "Link confirm is sent to your email." +
            " Please click link in 30 minutes to confirm registration.";
    String REGISTER_SUCCESSFULLY = "Register successfully";
    String LOGIN_SUCCESSFULLY = "Login successfully";
    String REGISTRATION_TOKEN_NOT_FOUND = "Registration token not found!";
    String REGISTRATION_TOKEN_IS_EXPIRED = "Registration token is expired";
    String USER_VERIFIED_REGISTRATION = "User verified registration";
    String USER_WAS_NOT_VERIFIED = "User wasn't verified";

    String SUBJECT_MAIL = "Mail verify registration";
    String ERROR_WHILE_SENDING_EMAIL = "Error while sending email";
}
