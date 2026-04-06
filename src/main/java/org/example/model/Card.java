package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.CardStatus;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String encryptedPan;
    @Column(length = 4,nullable = false)
    private String last4;
    @Column(nullable = false)
    private String holderName;
    @Column(nullable = false)
    private Integer expiryMonth;
    @Column(nullable = false)
    private Integer expiryYear;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus status;
    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal balance = BigDecimal.valueOf(0);
    @ManyToOne()
    @JoinColumn(nullable = false)
    private  Users user;
}
