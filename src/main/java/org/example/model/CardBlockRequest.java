package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.CardBlockStatusEnum;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CardBlockRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardBlockStatusEnum status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Card card;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Users user;

    @Column
    private String reason;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}
