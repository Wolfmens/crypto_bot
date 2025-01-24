package com.skillbox.cryptobot.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActualizerPrice {

    private final CryptoCurrencyService cryptoCurrencyService;
    private Double currentPrice;

    @Scheduled(cron = "0 */2 * * * *")
    public void updatingPrice() {
        try {
            currentPrice = cryptoCurrencyService.getBitcoinPrice();
            log.info("Цена обновлена, текущая цена: {}", currentPrice);
        } catch (IOException e) {
            log.error("Ошибка получения информации о текущей стоимости: {}", e.getMessage());
        }
    }

    @PostConstruct
    public void setCurrentPrice() {
        updatingPrice();
    }

    public Double getActualPrice() {
        return currentPrice;
    }
}
