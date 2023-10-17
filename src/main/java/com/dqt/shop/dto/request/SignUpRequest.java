package com.dqt.shop.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpRequest {

    private String email;

    private String password;

    private String fullName;

    private String phone;


}
