package com.skillbox.cryptobot.service.impl;

import com.skillbox.cryptobot.dto.SubscriberDto;
import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.excpetion.NotFoundException;
import com.skillbox.cryptobot.mapper.SubscriberMapper;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import com.skillbox.cryptobot.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;

    private final SubscriberMapper subscriberMapper;

    @Override
    @Transactional
    public Subscriber create(SubscriberDto subscriberDto) {
        return subscriberRepository.save(subscriberMapper.from(subscriberDto));
    }

    @Override
    public boolean isSubscribeExist(Long botId) {
        return subscriberRepository.existsByBotID(botId);
    }

    @Override
    @Transactional
    public Subscriber updatePriceSubscription(Long subscriberBotd, Double newPriceSubscribe) {
        Subscriber subscriberByBotID = findByBotID(subscriberBotd);
        subscriberByBotID.setPriceSubscription(newPriceSubscribe);

        return subscriberRepository.save(subscriberByBotID);
    }

    public Subscriber findByBotID(Long subscriberBotd) {
        return subscriberRepository.findByBotID(subscriberBotd).orElseThrow(
                () -> new NotFoundException("Пользователь для подписки не найден")
        );
    }

    @Override
    public List<Subscriber> findAllWhereCurrentPriceLessSubscription(Double currentPrice) {
        return subscriberRepository.findAllWhereCurrentPriceLessSubscription(currentPrice);
    }
}
