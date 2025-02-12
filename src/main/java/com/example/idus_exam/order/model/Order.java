package com.example.idus_exam.order.model;

import com.example.idus_exam.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
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

    @Column(length = 12, unique = true)
    @Size(max = 12, message = "주문번호는 최대 12자까지 허용됩니다.")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "주문번호는 영문 대문자와 숫자만 허용됩니다.")
    private String orderId;

    @Column(length = 100)
    @Size(max = 100, message = "제품명은 최대 100자까지 허용됩니다.")
    private String productName;

    private OffsetDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;
}
