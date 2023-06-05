package com.user.api.service;

import java.util.Objects;


import com.user.api.model.dto.response.UserResponseDto;
import com.user.api.model.User;
import com.user.api.model.dto.request.UserRequestDto;
import com.user.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userRepository.save(mapper.map(userRequestDto, User.class));
        return mapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto findUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return Objects.isNull(user) ? null : mapper.map(user, UserResponseDto.class);

    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        if (userRepository.findById(id).isPresent()) {
            User user = mapper.map(userRequestDto, User.class);
            user.setId(id);
            return mapper.map(userRepository.save(user), UserResponseDto.class);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
