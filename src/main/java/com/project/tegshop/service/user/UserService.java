package com.project.tegshop.service.user;

import com.project.tegshop.dto.AddressDto;
import com.project.tegshop.dto.ChangePasswordDto;
import com.project.tegshop.exception.AddressNotFoundException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.Address;
import com.project.tegshop.model.UserEntity;

import java.util.List;

public interface UserService{

    String registerSeller() throws UserNotFoundException;

    String addCartToOldUser(Integer id) throws UserNotFoundException;

    String getCurrentUserEmail();
    UserEntity getCurrentUser() throws UserNotFoundException;

    Address addAddress(AddressDto addressDto) throws UserNotFoundException;
    List<Address> getAddress() throws UserNotFoundException;

    AddressDto updateAddress(Integer addressId, AddressDto addressDto) throws UserNotFoundException, AddressNotFoundException;

    String deleteAddress(Integer addressId) throws AddressNotFoundException, UserNotFoundException;

    List<UserEntity> getAllUsers();

    String deleteUserById(Integer id) throws UserNotFoundException;

    String changePassword(ChangePasswordDto changePasswordDto) throws UserNotFoundException, UserException;
}
