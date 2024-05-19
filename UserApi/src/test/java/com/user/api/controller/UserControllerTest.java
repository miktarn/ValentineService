package com.user.api.controller;

import com.user.api.model.dto.request.UserRequestDto;
import com.user.api.model.dto.response.UserResponseDto;
import com.user.api.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserControllerTest {
    UserService userService;
    UserController userController;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void testCreateUser() {
        UserRequestDto testRequest = new UserRequestDto("testLogin", "testPassword", "testEmail");
        UserResponseDto responseDto = new UserResponseDto(1L, "testLogin", "testEmail");
        Mockito.doReturn(responseDto).when(userService).createUser(testRequest);

        UserResponseDto actualResponse = userController.createUser(testRequest);

        Mockito.verify(userService).createUser(testRequest);
        UserResponseDto expectedResponse = new UserResponseDto(1L, "testLogin", "testEmail");
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void findUserTest() {
        long inputId = 1L;
        UserResponseDto responseDto = new UserResponseDto(1L, "testLogin", "testEmail");
        Mockito.doReturn(responseDto).when(userService).findUser(inputId);

        UserResponseDto actualResponse = userController.findUser(inputId);

        Mockito.verify(userService).findUser(inputId);
        UserResponseDto expectedResponse = new UserResponseDto(1L, "testLogin", "testEmail");
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateUserTest() {
        long inputId = 1L;
        UserRequestDto testRequest = new UserRequestDto("testLogin", "testPassword", "testEmail");
        UserResponseDto responseDto = new UserResponseDto(1L, "testLogin", "testEmail");
        Mockito.doReturn(responseDto).when(userService).updateUser(inputId, testRequest);

        UserResponseDto actualResponse = userController.updateUser(inputId, testRequest);

        Mockito.verify(userService).updateUser(inputId, testRequest);
        UserResponseDto expectedResponse = new UserResponseDto(1L, "testLogin", "testEmail");
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteUserTest() {
        userController.deleteUser(1L);

        Mockito.verify(userService).deleteUser(1L);
    }
}