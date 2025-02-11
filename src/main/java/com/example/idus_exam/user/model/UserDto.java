package com.example.idus_exam.user.model;

import com.example.idus_exam.order.model.Order;
import lombok.*;

import java.util.List;

public class UserDto {
    @Getter
    public static class UserSignup {
        private String username;
        private String nickname;
        private String password;
        private String email;
        private int phone;
        private String sex = null;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .nickname(nickname)
                    .password(password)
                    .email(email)
                    .phone(phone)
                    .sex(sex)
                    .build();
        }
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Getter @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse{
        private Long idx;
        private String username;
        private String nickname;
        private String password;
        private String email;
        private int phone;
        private String sex;

        private List<Order> orderList;

        public static UserResponse from(User user){
            return UserResponse.builder()
                    .idx(user.getIdx())
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .sex(user.getSex())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserListResponse {
        private Long idx;
        private String username;
        private String nickname;
        private String email;
        private int phone;
        private String sex;
        private Order lastOrder;
    }
}
