package com.example.idus_exam.user;

import com.example.idus_exam.order.Order;
import com.example.idus_exam.order.OrderRepository;
import com.example.idus_exam.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final OrderRepository orderRepository;

    @GetMapping("/orders")
    public List<Order> getUserOrders(@AuthenticationPrincipal User user) {
        return orderRepository.findByUser(user);
    }
}
