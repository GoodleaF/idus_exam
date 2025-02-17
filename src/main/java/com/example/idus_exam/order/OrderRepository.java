package com.example.idus_exam.order;

import com.example.idus_exam.order.model.Order;
import com.example.idus_exam.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    List<Order> findByUserEmail(String email);

    Order findFirstByUserOrderByPaymentDateDesc(User user);
}
