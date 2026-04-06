package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Card fromCard;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Card toCard;
    @Column()
    private LocalDateTime createdAt;
    @PrePersist
    void setCreatedAt(){
        this.createdAt=LocalDateTime.now();
    }
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransferStatus status;
    @Column(nullable = false,scale = 2,precision = 19)
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Users user;
}
