package com.example.Baesh.Controller;


import com.example.Baesh.DTO.AuthResponse;
import com.example.Baesh.DTO.LoginRequest;
import com.example.Baesh.DTO.SignUpRequset;
import com.example.Baesh.JWT.JwtUtil;
import com.example.Baesh.Service.UserSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserSignUp userSignUp;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserSignUp userSignUp){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userSignUp =userSignUp;
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserId(),request.getPassword()));
            String token = jwtUtil.generateToken(request.getUserId());

            return ResponseEntity.ok(new AuthResponse(token));
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> singUp(@RequestBody SignUpRequset signUpRequset){
        try {
            String res = userSignUp.SignUp(signUpRequset);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("SignUp failure");
        }
    }

}
