package io.github.guennhatking.libra_auction.controllers;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.guennhatking.libra_auction.mappers.UserMapper;
import io.github.guennhatking.libra_auction.models.person.NguoiDung;
import io.github.guennhatking.libra_auction.services.UserService;
import io.github.guennhatking.libra_auction.viewmodels.response.ServerAPIResponse;
import io.github.guennhatking.libra_auction.viewmodels.response.UserResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService) {
        this.userService = userService;
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServerAPIResponse<UserResponse>> getUserInfo(@PathVariable String id) {
        Optional<NguoiDung> user = userService.findById(id);

        if (user.isPresent()) {
            UserResponse userResponse = userMapper.toResponse(user.get());
            return ResponseEntity.status(HttpStatus.OK).body(ServerAPIResponse.success(userResponse));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServerAPIResponse.error("User not found"));
        }
    }
}
