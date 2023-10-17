package com.dqt.shop.service;

import com.dqt.shop.entity.RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String email);

    Optional<RefreshToken> findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);

    int deleteByEmail(String email);
}
