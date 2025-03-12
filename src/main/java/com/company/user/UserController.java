package com.company.user;

import com.company.user.dtos.request.UserCr;
import com.company.user.dtos.response.UserResp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResp> create(@RequestBody UserCr cr) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(cr));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResp> update(
            @PathVariable UUID id,
            @RequestBody UserCr cr
    ) {
        return ResponseEntity.ok(userService.update(id, cr));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResp> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResp>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 