package com.project.tegshop.service.user;

import com.project.tegshop.exception.UserException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService{

    String registerSeller() throws UserNotFoundException;

    String addCartToOldUser(Integer id) throws UserNotFoundException;

    String getCurrentUserEmail();
    UserEntity getCurrentUser() throws UserNotFoundException;
}
