package com.dqt.shop.entity;


import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "refresh_token")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private Instant expiryDate;
}
