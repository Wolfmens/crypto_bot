package com.skillbox.cryptobot.service.impl;

import com.skillbox.cryptobot.dto.SchedulerDto;
import com.skillbox.cryptobot.enam.ScheduledUpdatePriceStrategy;
import com.skillbox.cryptobot.service.ScheduledService;
import com.skillbox.cryptobot.utils.TextUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActualizerPrice {

    private final CryptoCurrencyService cryptoCurrencyService;
    private final ScheduledService scheduledService;

    @Value("${binance.update-current-price.value}")
    public Long updateCurrentPrice;

    @Value("${binance.update-current-price.unit}")
    public TimeUnit timeUnit;

    private Double currentPrice;

    public void updatingPrice() {
        try {
            currentPrice = cryptoCurrencyService.getBitcoinPrice();
            log.info("Цена обновлена, текущая цена: {}", TextUtil.toString(currentPrice) + " USD");
        } catch (IOException e) {
            log.error("Ошибка получения информации о текущей стоимости: {}", e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        SchedulerDto schedulerDto = SchedulerDto
                .builder()
                .task(this::updatingPrice)
                .duration(ScheduledUpdatePriceStrategy.UPDATE_PRICE.getDuration(updateCurrentPrice, timeUnit))
                .build();

        scheduledService.actionBySchedule(schedulerDto);
    }

    public Double getActualPrice() {
        return currentPrice;
    }
}
