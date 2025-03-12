package com.company.auth;

import com.company.user.enums.Status;
import com.company.user.UserEntity;
import com.company.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthMapper authMapper;

    public AuthResp login(AuthDto authDto) {
//        return userRepository
//                .findByEmailAndPassword(authDto.getEmail(), bCryptPasswordEncoder.encode(authDto.getPassword()))
//                .orElseThrow();

        String email = authDto.getEmail();

        UserEntity user = userRepository
                .findByEmailAndVisibilityTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found!!!"));

        boolean matches = bCryptPasswordEncoder
                .matches(authDto.getPassword(), user.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid Password!!!");
        }


        return authMapper.apply( userRepository.save(user)) ;
    }

    public AuthResp register(RegisterDto registerDto) {

        UserEntity userEntity = new UserEntity();
        userEntity.setName(registerDto.getFullName());
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        userEntity.setRole(registerDto.getRole());
        userEntity.setStatus(Status.REGISTER);

        return authMapper.apply( userRepository.save(userEntity)) ;
    }

    public Page<UserEntity> getAll(int page, int size) {
//        return userRepository.findAll(PageRequest.of(page, size));
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public Page<UserEntity> getAllByVisiblity(int page, int size, boolean visibility) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Sort sort = Sort.by("createdAt")
                .descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return
                userRepository
                        .findAllByVisibility(visibility, pageable);
    }
}
