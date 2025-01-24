package com.skillbox.cryptobot.service.impl;

import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticePriceService {

    private final SubscriberService subscriberService;
    private final ActualizerPrice actualizerPrice;

    private final CryptoBot cryptoBot;

    @Scheduled(cron = "0 */10 * * * *")
    public void noticeUser() {
        double currentPrice = actualizerPrice.getActualPrice();
        log.info("Current price: {}", currentPrice);
        List<Subscriber> listUsersForNotice = subscriberService.findAllWhereCurrentPriceLessSubscription(currentPrice);
        if (!listUsersForNotice.isEmpty()) {
            CompletableFuture.runAsync(() ->  listUsersForNotice.forEach(subscriber -> {
                        cryptoBot.sendNoticeSubscriptionPriceUsers(
                                subscriber.getBotID().toString(),
                                new BigDecimal(String.valueOf(currentPrice))
                                        .setScale(3, RoundingMode.HALF_UP).doubleValue());
                        log.info("Уведомление пользователя: {} (bot ID: {}): ",
                                subscriber.getId(),
                                subscriber.getBotID());
                    })
            );
        } else {
            log.info("Подписантов на большую цену чем сейчас нет");
        }
    }
}
