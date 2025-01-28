package com.skillbox.cryptobot.service.impl;

import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.dto.SchedulerDto;
import com.skillbox.cryptobot.enam.ScheduledUpdatePriceStrategy;
import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.service.ScheduledService;
import com.skillbox.cryptobot.service.SubscriberService;
import com.skillbox.cryptobot.utils.TextUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticePriceService {

    private final SubscriberService subscriberService;
    private final ActualizerPrice actualizerPrice;
    private final ScheduledService scheduledService;

    private final CryptoBot cryptoBot;

    @Value("${telegram.bot.notify.delay.value}")
    public Long notifyDelayPrice;

    @Value("${telegram.bot.notify.delay.unit}")
    public TimeUnit timeUnit;

    private void noticeUser() {
        double currentPrice = actualizerPrice.getActualPrice();
        log.info("Current price: {}", currentPrice);
        List<Subscriber> listUsersForNotice = subscriberService.findAllWhereCurrentPriceLessSubscription(currentPrice);
        if (!listUsersForNotice.isEmpty()) {
            CompletableFuture.runAsync(() -> listUsersForNotice.forEach(subscriber -> {
                        cryptoBot.sendNoticeSubscriptionPriceUsers(
                                subscriber.getBotID().toString(),
                                TextUtil.toString(currentPrice) + " USD");
                        log.info("Уведомление пользователя: {} (bot ID: {}): ",
                                subscriber.getId(),
                                subscriber.getBotID());
                    })
            );
        } else {
            log.info("Подписантов на большую цену чем сейчас нет");
        }
    }

    @PostConstruct
    public void init() {
        SchedulerDto schedulerDto = SchedulerDto
                .builder()
                .task(this::noticeUser)
                .duration(ScheduledUpdatePriceStrategy.NOTICE_PRICE.getDuration(notifyDelayPrice, timeUnit))
                .build();

        scheduledService.actionBySchedule(schedulerDto);
    }
}
