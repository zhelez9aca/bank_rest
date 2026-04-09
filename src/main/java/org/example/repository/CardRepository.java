package org.example.repository;

import org.example.enums.CardStatusEnum;
import org.example.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUserId(Long id);

    Page<Card> findByUserId(Long userId, Pageable pageable);

    Page<Card> findByUserIdAndStatus(Long userId, CardStatusEnum status, Pageable pageable);

    Page<Card> findByStatus(CardStatusEnum status, Pageable pageable);
}
