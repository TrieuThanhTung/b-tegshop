package com.project.tegshop.service.user;

import com.project.tegshop.exception.UserException;
import com.project.tegshop.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService{

    String registerSeller() throws UserNotFoundException;

    String getCurrentUserEmail();
}
