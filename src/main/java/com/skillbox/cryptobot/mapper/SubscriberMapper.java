package com.skillbox.cryptobot.mapper;

import com.skillbox.cryptobot.dto.SubscriberDto;
import com.skillbox.cryptobot.entity.Subscriber;
import org.springframework.stereotype.Component;

@Component
public class SubscriberMapper {

    public Subscriber from(SubscriberDto subscriberDto) {
        return Subscriber.builder()
                .botID(subscriberDto.getBotID())
                .priceSubscription(subscriberDto.getPriceSubscription())
                .build();
    }

}
