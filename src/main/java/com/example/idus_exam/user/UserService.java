package com.example.idus_exam.user;

import com.example.idus_exam.emailverify.EmailVerifyService;
import com.example.idus_exam.order.OrderRepository;
import com.example.idus_exam.user.model.User;
import com.example.idus_exam.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final EmailVerifyService emailVerifyService;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserDto.UserSignup dto){
        User user = userRepository.save(dto.toEntity());

        emailVerifyService.signup(user.getIdx(), user.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(email);

        if(result.isPresent()){
            User user = result.get();
            return user;
        }
        return null;
    }

    public void verify(String uuid) {
        User user = emailVerifyService.verify(uuid);
        if(user != null) {
            user.verify();
            userRepository.save(user);
        }
    }

    public Page<UserDto.UserListResponse> getUserListWithLastOrder(String username, String email, Pageable pageable) {
        Page<User> userPage = userRepository.findByUsernameContainingAndEmailContaining(username, email, pageable);
        List<UserDto.UserListResponse> listResponses = userPage.getContent().stream().map(user -> {
            var lastOrder = orderRepository.findFirstByUserOrderByPaymentDateDesc(user);
            return UserDto.UserListResponse.builder()
                    .idx(user.getIdx())
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .sex(user.getSex())
                    .lastOrder(lastOrder)
                    .build();
        }).collect(Collectors.toList());
        return new PageImpl<>(listResponses, pageable, userPage.getTotalElements());
    }
}
