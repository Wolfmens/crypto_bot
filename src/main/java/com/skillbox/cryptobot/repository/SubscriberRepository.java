package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {

    boolean existsByBotID(Long botID);

    Optional<Subscriber> findByBotID(Long subscriberBotd);

    @Query(value = """
            SELECT s FROM Subscriber s
            WHERE s.priceSubscription IS NOT NULL
            AND s.priceSubscription > :currentPrice
            """)
    List<Subscriber> findAllWhereCurrentPriceLessSubscription(Double currentPrice);
}
