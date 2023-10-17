package com.dqt.shop.service.impl;

import com.dqt.shop.constant.Constant;
import com.dqt.shop.dto.request.SignUpRequest;
import com.dqt.shop.dto.response.ResponseMessage;
import com.dqt.shop.entity.Role;
import com.dqt.shop.entity.User;
import com.dqt.shop.repository.RoleRepository;
import com.dqt.shop.repository.UserRepository;
import com.dqt.shop.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public ResponseMessage signUpUser(SignUpRequest signUpUser) {
        if (userRepository.existsByEmail(signUpUser.getEmail())) {
            return ResponseMessage.error("Email already exists");
        } else if (userRepository.existsByPhone(signUpUser.getPhone())) {
            return ResponseMessage.error("Phone already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(signUpUser, user);
        user.setPassword(encoder.encode(signUpUser.getPassword()));
        String email = signUpUser.getEmail();
        int i = email.indexOf("@");
        user.setUserCode(email.substring(0, i));
        user.setStatus(Boolean.TRUE);
        user.setCreatedAt(new Date());

        Role roleUser = roleRepository.findByRoleName(Constant.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        user.setRoles(roles);

        User saved = userRepository.save(user);
        return ResponseMessage.ok("User saved successfully", saved);
    }
}
