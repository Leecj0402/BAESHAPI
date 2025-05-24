package com.example.Baesh.Service;

import com.example.Baesh.DTO.SignUpRequset;
import com.example.Baesh.Entity.UserE;
import com.example.Baesh.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserSignUp {

    private final UserRepository userRepository;

    public String SignUp(SignUpRequset signUpRequset){
        try {
            String encodedPassword = new BCryptPasswordEncoder().encode(signUpRequset.getPassword());
            UserE user = new UserE(signUpRequset.getUserId(),encodedPassword,signUpRequset.getName(), signUpRequset.getEmail(),
                    signUpRequset.getPhone_number(), signUpRequset.getResidence(), signUpRequset.getRule());
            userRepository.save(user);
            return "true";
            // 반환 값은 회원가입 한후에 프론트에 전달할 데이터
            // 반환 데이터 필요 없다면 그냥 void 타입으로 설정 해고 상태 코드만 반환해도 무방
            // void 타입으로 할때는 컨트롤러에 반환 값도 변경 해야함
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
