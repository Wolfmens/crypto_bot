package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.dto.SubscriberDto;
import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.excpetion.NotFoundException;
import com.skillbox.cryptobot.service.SubscriberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

/**
 * Обработка команды отмены подписки на курс валюты
 */
@Service
@Slf4j
public class UnsubscribeCommand extends CommandBotProcessing implements IBotCommand {

    public UnsubscribeCommand(SubscriberService subscriberService) {
        super(subscriberService);
    }

    @Override
    public String getCommandIdentifier() {
        return "unsubscribe";
    }

    @Override
    public String getDescription() {
        return "Отменяет подписку пользователя";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long userIdInBot = message.getChatId();
        try {
            log.error("Запрос на удаление подписки");
            Subscriber currentSubscriber = subscriberService.findByBotID(userIdInBot);
            subscriberService.updatePriceSubscription(currentSubscriber.getBotID(), null);
            sendMessage(absSender, currentSubscriber.getBotID(), "Подписка отменена");
            log.error("Подписка для пользователя: {}, удалена", currentSubscriber.getBotID());
        } catch (NotFoundException e) {
            log.error("Ошибка при запросе подписки: {}", e.getMessage());
            sendMessage(absSender, userIdInBot, e.getMessage());
        }
    }
}