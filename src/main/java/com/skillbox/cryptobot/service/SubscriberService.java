package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.dto.SubscriberDto;
import com.skillbox.cryptobot.entity.Subscriber;

import java.util.List;

public interface SubscriberService {

    Subscriber create(SubscriberDto subscriberDto);

    boolean isSubscribeExist(Long botId);

    Subscriber updatePriceSubscription(Long subscriberBotd, Double newPriceSubscribe);

    Subscriber findByBotID(Long subscriberBotd);

    List<Subscriber> findAllWhereCurrentPriceLessSubscription(Double currentPrice);
}
