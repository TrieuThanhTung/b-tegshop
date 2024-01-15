package com.project.tegshop.service.user;

import com.project.tegshop.dto.AddressDto;
import com.project.tegshop.dto.ChangePasswordDto;
import com.project.tegshop.dto.UpdateProfileDto;
import com.project.tegshop.dto.resetPassword.RequestEmail;
import com.project.tegshop.exception.AddressNotFoundException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.exception.UserNotFoundException;
import com.project.tegshop.model.*;
import com.project.tegshop.repository.AddressRepository;
import com.project.tegshop.repository.RegisterTokenRepository;
import com.project.tegshop.repository.ResetPasswordRepository;
import com.project.tegshop.repository.UserRepository;
import com.project.tegshop.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Value("${expiration.time.register.token}")
    private int expirationTime;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private RegisterTokenRepository registerTokenRepository;
    private ResetPasswordRepository resetPasswordRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           AddressRepository addressRepository,
                           RegisterTokenRepository registerTokenRepository,
                           ResetPasswordRepository resetPasswordRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.registerTokenRepository = registerTokenRepository;
        this.resetPasswordRepository = resetPasswordRepository;
        this.passwordEncoder = passwordEncoder;
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
    public String changePassword(ChangePasswordDto changePasswordDto)
            throws UserNotFoundException, UserException {
        UserEntity currentUser = getCurrentUser();

        if(!passwordEncoder.matches(changePasswordDto.getOldPassword(), currentUser.getPassword())) {
            throw new UserException(GenericMessage.PASSWORD_NOT_MATCH);
        }

        currentUser.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(currentUser);

        return GenericMessage.USER_CHANGE_PASSWORD;
    }

    @Override
    public String updateProfile(UpdateProfileDto updateProfileDto)
            throws UserNotFoundException {
        UserEntity currentUser = getCurrentUser();

        currentUser.setFirstName(updateProfileDto.getFirstName());
        currentUser.setLastName(updateProfileDto.getLastName());
        userRepository.save(currentUser);

        return GenericMessage.USER_UPDATE_PROFILE;
    }

    @Override
    public UserEntity getProfile() throws UserNotFoundException {
        UserEntity currentUser = getCurrentUser();

        return currentUser;
    }

    @Override
    public String sendMailResetPassword(RequestEmail requestEmail)
            throws UserNotFoundException, UserException {
        UserEntity user = userRepository.findByEmailId(requestEmail.getEmailId())
                .orElseThrow(() -> new UserNotFoundException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        Optional<ResetPasswordToken> rpToken = resetPasswordRepository.findByUser(user);
        if(rpToken.isPresent() && rpToken.get().getExpiredTime().isAfter(LocalDateTime.now())) {
            throw new UserException("Token for reset password is sent!. Please check your mail");
        }

        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setExpiredTime(LocalDateTime.now().plusMinutes(expirationTime));
        resetPasswordToken.setToken(UUID.randomUUID().toString().substring(0, 6));
        resetPasswordToken.setUser(user);
        resetPasswordRepository.save(resetPasswordToken);

        return resetPasswordToken.getToken();
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

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> userList = userRepository.findAll();

        return userList;
    }

    @Override
    public String deleteUserById(Integer id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(GenericMessage.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        RegisterToken registerToken = registerTokenRepository.findByUserId(id)
                        .orElseThrow(() -> new UserNotFoundException(GenericMessage.REGISTRATION_TOKEN_NOT_FOUND));

        registerTokenRepository.delete(registerToken);
        userRepository.delete(user);

        return GenericMessage.USER_DELETE;
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
