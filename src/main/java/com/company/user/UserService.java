package com.company.user;

import com.company.security.utils.contextHolder;
import com.company.user.dtos.request.UserCr;
import com.company.user.dtos.response.UserResp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserResp create(UserCr cr) {
        UserEntity entity = userMapper.toEntity(cr);
        entity.setPassword(passwordEncoder.encode(cr.password()));

        System.out.println(contextHolder.getUser());

        SecurityContextHolder.getContext();
        return userMapper.toResponse(userRepository.save(entity));
    }


    public UserResp update(UUID id, UserCr cr) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        entity.setName(cr.name());
        entity.setEmail(cr.email());
        if (cr.password() != null) {
            entity.setPassword(passwordEncoder.encode(cr.password()));
        }
        entity.setRole(cr.role());
        
        return userMapper.toResponse(userRepository.save(entity));
    }


    public UserResp getById(UUID id) {
        return userMapper.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }


    public List<UserResp> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }


    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
} 