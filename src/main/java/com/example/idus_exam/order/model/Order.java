package com.example.idus_exam.order.model;

import com.example.idus_exam.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private int orderId;
    private String productName;
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;
}
