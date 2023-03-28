package com.yigiter.service;

import com.yigiter.domain.Role;
import com.yigiter.domain.User;
import com.yigiter.domain.enums.RoleType;
import com.yigiter.dto.*;
import com.yigiter.dto.response.ResponseMessage;
import com.yigiter.repository.UserRepository;
import com.yigiter.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;

    }

    public User getUserByEmail(String email) {
        User user =userRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("Could not find user")
        );
          return user;
    }

    public void saveUser(RegisterRequest registerRequest) {

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("User already exists");
        }


        Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);


        String encodedPassword= passwordEncoder.encode(registerRequest.getPassword());


        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        user.setRoles(roles);

        userRepository.save(user);

    }


    public void deleteUserById(Long id) {
        User user = getById(id);


        if(user.getBuiltIn()){
            throw new RuntimeException(ResponseMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        userRepository.deleteById(id);

    }

    public Page<UserDTO> getUserPage(Pageable pageable) {
        Page<User> user =userRepository.findAll(pageable);
        Page<UserDTO> userDTOpages =user.map(
                getUser->{
                    UserDTO userDTO=userToUserDTO(getUser);
                return userDTO;
                }
        );
        return userDTOpages;
    }


    public UserDTO getPrincipal() {
        User user =getCurrentUser();
        UserDTO userDTO =userToUserDTO(user);
        return userDTO;

    }
    public UserDTO getUserById(Long id) {
        User user =getById(id);
        UserDTO userDTO =userToUserDTO(user);
        return userDTO;
    }
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        User user =getCurrentUser();

        if (user.getBuiltIn()){
            throw new RuntimeException(ResponseMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(),user.getPassword())){
            throw new RuntimeException(ResponseMessage.PASSWORD_NOT_MATCHED_MESSAGE);
        }

        String hashedPassword =passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);

    }

    @Transactional

    public void updateUser(UserUpdateRequest userUpdateRequest) {
        User user = getCurrentUser();

        if(user.getBuiltIn()){
            throw new RuntimeException(ResponseMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        // !!! email control
        //  old    new    Db      status
        //  A       A       A       valid
        //  A       D       B       valid
        //  A       B       C       invalid
        boolean emailExist = userRepository.existsByEmail(userUpdateRequest.getEmail());

        if(emailExist && !userUpdateRequest.getEmail().equals(user.getEmail())) {
            throw new RuntimeException(ResponseMessage.EMAIL_ALREADY_EXIST_MESSAGE);
        }
        userRepository.update(

                user.getId(),
                userUpdateRequest.getFirstName(),
                userUpdateRequest.getLastName(),
                userUpdateRequest.getPhoneNumber(),
                userUpdateRequest.getEmail());
    }

    public void updateUserAuth(Long id, AdminUserUpdateRequest adminUserUpdateRequest) {

        User user =getById(id);


        if(user.getBuiltIn()){
            throw new RuntimeException(ResponseMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        boolean emailExist = userRepository.existsByEmail(adminUserUpdateRequest.getEmail());
        if(emailExist && !adminUserUpdateRequest.getEmail().equals(user.getEmail())) {
            throw new RuntimeException(ResponseMessage.EMAIL_ALREADY_EXIST_MESSAGE);
        }

        if (adminUserUpdateRequest.getPassword()==null){
            adminUserUpdateRequest.setPassword(user.getPassword());
        }else {
            String encodedPassword =passwordEncoder.encode(adminUserUpdateRequest.getPassword());
            adminUserUpdateRequest.setPassword(encodedPassword);
        }

        Set<String> userStrRoles =adminUserUpdateRequest.getRoles();

        Set<Role> roles = convertRoles(userStrRoles);

        user.setFirstName(adminUserUpdateRequest.getFirstName());
        user.setLastName(adminUserUpdateRequest.getLastName());
        user.setEmail(adminUserUpdateRequest.getEmail());
        user.setPassword(adminUserUpdateRequest.getPassword());
        user.setPhoneNumber(adminUserUpdateRequest.getPhoneNumber());
        user.setBuiltIn(adminUserUpdateRequest.getBuiltIn());
        user.setRoles(roles);

        userRepository.save(user);
    }

    private Set<Role> convertRoles(Set<String> pRoles){
        Set<Role> roles = new HashSet<Role>();
        if (pRoles==null){

            Role userRole =roleService.findByType(RoleType.ROLE_CUSTOMER);
            roles.add(userRole);
        }else{
            pRoles.forEach(roleStr->{
                if(roleStr.equals(RoleType.ROLE_ADMIN.getName())){
                    Role adminRole =roleService.findByType(RoleType.ROLE_ADMIN);

                    roles.add(adminRole);
                }else {
                    Role userRole =roleService.findByType(RoleType.ROLE_CUSTOMER);
                    roles.add(userRole);
                }

            });

        }
        return roles;
    }



    public UserDTO userToUserDTO(User user){
        UserDTO userDTO=new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setBuiltIn(user.getBuiltIn());
        userDTO.setRoles(user.getRoles());

        return userDTO;

    }


    public User getCurrentUser() {
        String email= SecurityUtils.getCurrentUserLogin().orElseThrow(
                ()->new RuntimeException(ResponseMessage.PRINCIPAL_FOUND_MESSAGE)
        );

        User user =getUserByEmail(email);
        return user;

    }

    public User getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new RuntimeException(ResponseMessage.USER_NOT_FOUND_EXCEPTION)
        );
        return user;
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
