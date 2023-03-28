package com.yigiter.controller;

import com.yigiter.dto.AdminUserUpdateRequest;
import com.yigiter.dto.UpdatePasswordRequest;
import com.yigiter.dto.UserDTO;
import com.yigiter.dto.UserUpdateRequest;
import com.yigiter.dto.response.ResponseMessage;
import com.yigiter.dto.response.TResponse;
import com.yigiter.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/auth/pages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> getAllUsersByPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam(value = "direction",
                    required = false,
                    defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<UserDTO> userDTOPage = userService.getUserPage(pageable);
        return ResponseEntity.ok(userDTOPage);
    }

    @DeleteMapping("/auth/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TResponse> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        TResponse response = new TResponse(ResponseMessage.USER_DELETE_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(response);

    }
    //!!! Sisteme giriş yapan kullanıcının bilgisi...

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<UserDTO> getUser() {
        UserDTO userDTO = userService.getPrincipal();
        return ResponseEntity.ok(userDTO);
    }

    //GetUserById
    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    //Update Password
    @PatchMapping("/updatepassword")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<TResponse> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        userService.updatePassword(updatePasswordRequest);
        TResponse response = new TResponse();
        response.setMessage(ResponseMessage.PASSWORD_CHANGED_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateuser")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<TResponse> updateUser(
            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);

        TResponse response = new TResponse();
        response.setMessage(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}/updateuser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TResponse> updateUserAuth(@PathVariable Long id,
                                                     @Valid @RequestBody AdminUserUpdateRequest adminUserUpdateRequest) {
        userService.updateUserAuth(id, adminUserUpdateRequest);

        TResponse response = new TResponse();
        response.setMessage(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

}
