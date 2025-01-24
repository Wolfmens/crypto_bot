package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.excpetion.NotFoundException;
import com.skillbox.cryptobot.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Optional;

@Service
@Slf4j
public class GetSubscriptionCommand extends CommandBotProcessing implements IBotCommand {

    public GetSubscriptionCommand(SubscriberService subscriberService) {
        super(subscriberService);
    }

    @Override
    public String getCommandIdentifier() {
        return "get_subscription";
    }

    @Override
    public String getDescription() {
        return "Возвращает текущую подписку";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long userIdInBot = message.getChatId();
        try {
            Subscriber currentSubscriber = subscriberService.findByBotID(userIdInBot);
            log.info("Запрос на наличие подписки от: {}", currentSubscriber);
            Optional.ofNullable(currentSubscriber.getPriceSubscription())
                    .ifPresentOrElse(
                            price -> {
                                String textMessage = "Вы подписаны на стоимость биткоина " + price + " USD";
                                sendMessage(absSender, userIdInBot, textMessage);
                            },
                            () ->  {
                                String textMessage = "Активные подписки отсутствуют";
                                sendMessage(absSender, userIdInBot, textMessage);
                            });
        } catch (NotFoundException e) {
            log.error("Ошибка при запросе подписки: {}", e.getMessage());
            sendMessage(absSender, userIdInBot, e.getMessage());
        }
    }
}