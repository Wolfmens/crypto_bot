package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.SubscriberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@AllArgsConstructor
@Slf4j
public class CommandBotProcessing {

    public final SubscriberService subscriberService;

    public void sendMessage(AbsSender absSender, Long userIdInBot, String textMessage) {
        log.info("Отправка ответа по запросу актуальной подписки");
        SendMessage answer = new SendMessage();
        answer.setChatId(userIdInBot);
        answer.setText(textMessage);

        execute(absSender, answer);
    }

    public void execute(AbsSender absSender, SendMessage answer) {
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /start command", e);
        }
    }

}
