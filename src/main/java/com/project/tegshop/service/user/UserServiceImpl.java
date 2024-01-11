package com.project.tegshop.service.user;

import com.project.tegshop.exception.UserException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.Cart;
import com.project.tegshop.model.Role;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.repository.UserRepository;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public String registerSeller() throws UserNotFoundException {
        String userEmail = getCurrentUserEmail();
        UserEntity currentUser = userRepository.findByEmailId(userEmail)
                .orElseThrow(() -> new UserNotFoundException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        currentUser.setRole(Role.SELLER);
        userRepository.save(currentUser);

        return GenericMessage.REGISTER_SELLER_SUCCESSFULLY;
    }

    @Override
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public UserEntity getCurrentUser() throws UserNotFoundException {
        String userEmail = getCurrentUserEmail();

        return userRepository.findByEmailId(userEmail)
                .orElseThrow(() -> new UserNotFoundException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));
    }


    //
    @Override
    public String addCartToOldUser(Integer id) throws UserNotFoundException {
        UserEntity currentUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        currentUser.setCart(new Cart());
        userRepository.save(currentUser);

        return "oke";
    }
}
