package com.skillbox.cryptobot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.util.UUID;

@Entity
@Table(name = "subscribers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "bot_id", nullable = false)
    @Comment("ID подписанта в боте")
    private Long botID;

    @Column(name = "price_subscription")
    @Comment("Цена на которую подписался пользователь")
    private Double priceSubscription;

}
