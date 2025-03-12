package com.company.auth;


import com.company.user.UserEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResp login(@RequestBody AuthDto authDto) {
        return authService.login(authDto);
    }

    @PostMapping("/register")
    public AuthResp register(@RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @GetMapping("/get-all")
    public Page<UserEntity> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size) {
        return authService.getAll(page, size);
    }

    @GetMapping("/get-all-by/{visibility}")
    public Page<UserEntity> getAllByVisiblity(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size,
                                                              @PathVariable boolean visibility) {
        return authService.getAllByVisiblity(page, size, visibility);
    }

    @PostMapping("/default")
    public String login() {
        authService.register(
                RegisterDto.builder()
                        .email("test@gmail.com")
                        .password("test")
                        .build()
        );

        authService.register(
                RegisterDto.builder()
                        .email("alish@gmail.com")
                        .password("alish")
                        .build()
        );

        return "SUCCESS";
    }


    @PostConstruct
    public void test() {
        System.out.println("POSTConstruct");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void test2() {
        System.out.println("EventListener");
    }
}
