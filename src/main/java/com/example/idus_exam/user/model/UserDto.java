package com.example.idus_exam.user.model;

import com.example.idus_exam.order.model.Order;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

public class UserDto {
    @Getter
    public static class UserSignup {
        @NotBlank(message = "회원 이름은 필수입니다.")
        @Size(max = 20, message = "회원 이름은 최대 20자까지 허용됩니다.")
        @Pattern(regexp = "^[가-힣A-Za-z]+$", message = "회원 이름은 한글 및 영문 대소문자만 허용됩니다.")
        private String username;

        @NotBlank(message = "별명은 필수입니다.")
        @Size(max = 30, message = "별명은 최대 30자까지 허용됩니다.")
        @Pattern(regexp = "^[a-z]+$", message = "별명은 영문 소문자만 허용됩니다.")
        private String nickname;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 10, message = "비밀번호는 최소 10자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{10,}$",
                message = "비밀번호는 영문 대문자, 영문 소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 합니다.")
        private String password;

        @NotBlank(message = "이메일은 필수입니다.")
        @Size(max = 100, message = "이메일은 최대 100자까지 허용됩니다.")
        @Email(message = "유효한 이메일 형식을 입력하세요.")
        private String email;

        @NotBlank(message = "전화번호는 필수입니다.")
        @Size(max = 20, message = "전화번호는 최대 20자까지 허용됩니다.")
        @Pattern(regexp = "^\\d+$", message = "전화번호는 숫자만 허용됩니다.")
        private String phone;
        private String sex = null;

        public User toEntity(String encodedPassword) {
            return User.builder()
                    .username(username)
                    .nickname(nickname)
                    .password(encodedPassword)
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
        private String phone;
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
