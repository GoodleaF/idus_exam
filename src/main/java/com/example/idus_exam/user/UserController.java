package com.example.idus_exam.user;

import com.example.idus_exam.order.model.Order;
import com.example.idus_exam.order.OrderRepository;
import com.example.idus_exam.user.model.User;
import com.example.idus_exam.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final OrderRepository orderRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto.UserSignup dto) {
        userService.signup(dto);
        return ResponseEntity.ok("이메일 인증을 완료해주세요");
    }

    @GetMapping("/orders")
    public List<Order> getUserOrders(@AuthenticationPrincipal User user) {
        return orderRepository.findByUser(user);
    }

    @GetMapping("/myinfo")
    public ResponseEntity<UserDto.UserResponse> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserDto.UserResponse.from(user));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<UserDto.UserListResponse>> getUserList(
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var pageable = PageRequest.of(page, size, Sort.by("idx").descending());
        var userListPage = userService.getUserListWithLastOrder(username, email, pageable);
        return ResponseEntity.ok(userListPage);
    }
}
