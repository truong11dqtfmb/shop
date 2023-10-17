package com.dqt.shop.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String roleName;

}
