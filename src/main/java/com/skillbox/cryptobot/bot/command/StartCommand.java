package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.dto.SubscriberDto;
import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


/**
 * Обработка команды начала работы с ботом
 */
@Service
@Slf4j
public class StartCommand extends CommandBotProcessing implements IBotCommand {

    public StartCommand(SubscriberService subscriberService) {
        super(subscriberService);
    }

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Запускает бота";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long userIdInBot = message.getChatId();
        if (!subscriberService.isSubscribeExist(userIdInBot)) {
            Subscriber newSubscriber = subscriberService.create(SubscriberDto.builder()
                    .botID(userIdInBot)
                    .priceSubscription(null)
                    .build());

            log.info("Subscriber created {}", newSubscriber);
        } else {
            log.info("Subscriber already exists");
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        answer.setText("""
                Привет! Данный бот помогает отслеживать стоимость биткоина.
                Поддерживаемые команды:
                 /get_price - получить стоимость биткоина
                """);
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /start command", e);
        }
    }
}