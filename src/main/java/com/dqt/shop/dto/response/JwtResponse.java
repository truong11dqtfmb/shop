package com.dqt.shop.dto.response;

import com.dqt.shop.constant.Constant;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class JwtResponse {
    private String token;
    private String tokenType = Constant.TOKEN_TYPE;
    private String email;
    private String refreshToken;

    public JwtResponse(String token, String email, String refreshToken) {
        this.token = token;
        this.email = email;
        this.refreshToken = refreshToken;
    }
}
