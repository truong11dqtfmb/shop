package com.dqt.shop.controller;

import com.dqt.shop.dto.request.LoginRequest;
import com.dqt.shop.dto.request.SignUpRequest;
import com.dqt.shop.dto.request.TokenRequest;
import com.dqt.shop.dto.response.JwtResponse;
import com.dqt.shop.dto.response.ResponseMessage;
import com.dqt.shop.dto.response.TokenRefreshResponse;
import com.dqt.shop.entity.RefreshToken;
import com.dqt.shop.entity.User;
import com.dqt.shop.exception.TokenRefreshException;
import com.dqt.shop.repository.UserRepository;
import com.dqt.shop.security.jwt.JwtUtils;
import com.dqt.shop.security.service.UserDetailsImpl;
import com.dqt.shop.service.AuthService;
import com.dqt.shop.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpUser) {
        try {
            ResponseMessage message = authService.signUpUser(signUpUser);
            if (message.isSuccess()) {
                return ResponseEntity.ok(message);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sign Up User Failed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginUser){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getEmail(),loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);


        Optional<User> userOptional = userRepository.findByEmail(loginUser.getEmail());
        if(!userOptional.isPresent()){
            return  ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResponseMessage.error("User not exists"));
        }

        User user = userOptional.get();
        if(!user.getStatus()){
            return  ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(ResponseMessage.error("User is disabled"));
        }

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginUser.getEmail());
        return ResponseEntity.ok(new JwtResponse(token, loginUser.getEmail(), refreshToken.getToken()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRequest request) {
        String requestRefreshToken = request.getToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getEmail)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,"Refresh token not found"));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        refreshTokenService.deleteByEmail(userDetails.getUsername());
        return ResponseEntity.ok(ResponseMessage.error("Logout successfully"));
    }


}
