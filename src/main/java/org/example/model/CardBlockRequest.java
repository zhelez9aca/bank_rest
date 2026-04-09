package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.CardBlockStatus;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Table
@Entity
@Getter
@Setter
public class BlockCartRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardBlockStatus status;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Card card;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Users user;
    @Column
    private String reason;
    @Column
    private LocalDateTime createdAt;
    @PrePersist
    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

}
