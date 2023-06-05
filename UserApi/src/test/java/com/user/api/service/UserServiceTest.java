package com.user.api.service;

import java.util.Optional;


import com.user.api.model.User;
import com.user.api.model.dto.request.UserRequestDto;
import com.user.api.model.dto.response.UserResponseDto;
import com.user.api.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

class UserServiceTest {
    UserRepository repositoryMock;
    UserService userService;

    @BeforeEach
    void setUp() {
        repositoryMock = Mockito.mock(UserRepository.class);
        userService = new UserService(repositoryMock, new ModelMapper());
    }

    @Test
    void createUserTest() {
        UserRequestDto request = new UserRequestDto("testLogin", "testPassword", "testEmail");
        User inputUser = new User(null, "testLogin", "testPassword", "testEmail");
        User outputUser = new User(1L, "testLogin", "testPassword", "testEmail");
        Mockito.doReturn(outputUser).when(repositoryMock).save(inputUser);

        UserResponseDto actualResponse = userService.createUser(request);

        UserResponseDto expectedResponse = new UserResponseDto(1L, "testLogin", "testEmail");
        Mockito.verify(repositoryMock).save(inputUser);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void findUserTest() {
        long inputId = 1L;
        Optional<User> outputUser = Optional.of(new User(1L, "testLogin", "testPassword", "testEmail"));
        Mockito.doReturn(outputUser).when(repositoryMock).findById(inputId);

        UserResponseDto actualResponse = userService.findUser(inputId);

        Mockito.verify(repositoryMock).findById(inputId);
        UserResponseDto expectedResponse = new UserResponseDto(1L, "testLogin", "testEmail");
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateUserTest_userNotExists() {
        long inputId = 1L;
        UserRequestDto request = new UserRequestDto("testLogin", "testPassword", "testEmail");
        Mockito.doReturn(Optional.empty()).when(repositoryMock).findById(inputId);

        UserResponseDto userResponseDto = userService.updateUser(inputId, request);

        Mockito.verify(repositoryMock).findById(inputId);
        Assertions.assertNull(userResponseDto);
        Mockito.verify(repositoryMock, Mockito.never()).save(Mockito.any());
    }

    @Test
    void updateUserTest_userExists() {
        long inputId = 1L;
        UserRequestDto request = new UserRequestDto("testLogin", "testPassword", "testEmail");
        Optional<User> outputUser = Optional.of(new User(1L, "otherLogin", "otherPassword", "otherEmail"));
        Mockito.doReturn(outputUser).when(repositoryMock).findById(inputId);
        User savedUser = new User(1L, "testLogin", "testPassword", "testEmail");
        Mockito.doReturn(savedUser).when(repositoryMock).save(savedUser);

        UserResponseDto actualResponse = userService.updateUser(inputId, request);

        Mockito.verify(repositoryMock).findById(inputId);
        Mockito.verify(repositoryMock).save(new User(1L, "testLogin", "testPassword", "testEmail"));
        UserResponseDto expectedResponse = new UserResponseDto(1L, "testLogin", "testEmail");
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void  deleteUserTest() {
        long inputId = 1L;

        userService.deleteUser(inputId);

        Mockito.verify(repositoryMock).deleteById(inputId);
    }
}