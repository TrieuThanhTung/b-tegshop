package com.project.tegshop.service.user;

import com.project.tegshop.dto.AddressDto;
import com.project.tegshop.exception.AddressNotFoundException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.Address;
import com.project.tegshop.model.Cart;
import com.project.tegshop.model.Role;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.repository.AddressRepository;
import com.project.tegshop.repository.UserRepository;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }


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

    @Override
    public Address addAddress(AddressDto addressDto) throws UserNotFoundException {
        UserEntity currentUser = getCurrentUser();

        Address address = new Address();
        address.setDescription(addressDto.getDescription());
        address.setStreet(addressDto.getStreet());
        address.setDistrict(addressDto.getDistrict());
        address.setProvince(addressDto.getProvince());

        currentUser.getAddresses().add(address);
        userRepository.save(currentUser);

        return address;
    }

    @Override
    public List<Address> getAddress() throws UserNotFoundException {
        UserEntity currentUser = getCurrentUser();

        return currentUser.getAddresses();
    }


    @Override
    public AddressDto updateAddress(Integer addressId, AddressDto addressDto) throws UserNotFoundException, AddressNotFoundException {
        UserEntity currentUser = getCurrentUser();

        List<Address> addresses = currentUser.getAddresses();

        boolean isHasAddress = false;

        for(Address ad : addresses) {
            if(Objects.equals(ad.getAddressId(), addressId)) {
                ad.setDescription(addressDto.getDescription());
                ad.setStreet(addressDto.getStreet());
                ad.setDistrict(addressDto.getDistrict());
                ad.setProvince(addressDto.getProvince());
                addressRepository.save(ad);

                isHasAddress = true;
                break;
            }
        }

        if(!isHasAddress) {
            throw new AddressNotFoundException(GenericMessage.ADDRESS_NOT_FOUND);
        }

        return addressDto;
    }

    @Override
    public String deleteAddress(Integer addressId) throws AddressNotFoundException, UserNotFoundException {
        UserEntity currentUser = getCurrentUser();

        List<Address> addresses = currentUser.getAddresses();

        boolean isHasAddress = false;

        for(Address ad : addresses) {
            if(Objects.equals(ad.getAddressId(), addressId)) {
                addresses.remove(ad);
                addressRepository.delete(ad);
                isHasAddress = true;
                break;
            }
        }

        if(!isHasAddress) {
            throw new AddressNotFoundException(GenericMessage.ADDRESS_NOT_FOUND);
        } else {
            userRepository.save(currentUser);
        }

        return GenericMessage.ADDRESS_DELETE;
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
