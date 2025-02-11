package com.example.idus_exam.user;

import com.example.idus_exam.emailverify.EmailVerifyService;
import com.example.idus_exam.user.model.User;
import com.example.idus_exam.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
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
}
