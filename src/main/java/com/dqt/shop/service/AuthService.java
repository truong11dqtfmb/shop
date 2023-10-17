package com.dqt.shop.service;

import com.dqt.shop.dto.request.SignUpRequest;
import com.dqt.shop.dto.response.ResponseMessage;
import org.springframework.stereotype.Service;

public interface AuthService {

    ResponseMessage signUpUser(SignUpRequest signUpUser);

}
