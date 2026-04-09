package org.example.repository;

import org.example.enums.CardBlockStatusEnum;
import org.example.model.CardBlockRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardBlockRepository extends JpaRepository<CardBlockRequest, Long> {
    Page<CardBlockRequest> findAllByStatus(CardBlockStatusEnum status, Pageable pageable);

    boolean existsByCardIdAndStatus(Long cardId, CardBlockStatusEnum status);
}
