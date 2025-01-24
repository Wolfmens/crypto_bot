package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.dto.SubscriberDto;
import com.skillbox.cryptobot.entity.Subscriber;

public interface SubscriberService {

    Subscriber create(SubscriberDto subscriberDto);

    boolean isSubscribeExist(Long botId);

    Subscriber updatePriceSubscription(Long subscriberBotd, Long newPriceSubscribe);

    Subscriber findByBotID(Long subscriberBotd);

}
