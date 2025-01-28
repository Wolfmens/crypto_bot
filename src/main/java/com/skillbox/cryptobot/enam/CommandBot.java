package com.skillbox.cryptobot.enam;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandBot {
    START("/start", "Запуск приложения"),
    GET_PRICE("/get_price", "Актуальные данные по текущей стоимости"),
    SUBSCRIBE("/subscribe", "Сделать новую подписку (пр. /subscribe 106000)"),
    GET_SUBSCRIPTION("/get_subscription", "Актуальные данные по текущей подписке"),
    UNSUBSCRIBE("/unsubscribe", "Удалить подписку");

    private final String command;
    private final String description;

}
