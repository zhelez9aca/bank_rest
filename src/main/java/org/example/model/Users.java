package org.example.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.RoleEnum;
import org.example.enums.UserStatusEnum;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @Column(unique = true,nullable = false)
    private String login;
    @Column(nullable = false)
    private String passwordHash;
    @Column
    private LocalDateTime createdAt;
    @PrePersist
    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }
    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatusEnum userStatus = UserStatusEnum.ACTIVE;
}
