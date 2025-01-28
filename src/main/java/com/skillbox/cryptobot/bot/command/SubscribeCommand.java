package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.excpetion.NotFoundException;
import com.skillbox.cryptobot.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обработка команды подписки на курс валюты
 */
@Service
@Slf4j
public class SubscribeCommand extends CommandBotProcessing implements IBotCommand {

    private final IBotCommand getPriceCommand;

    public SubscribeCommand(SubscriberService subscriberService, IBotCommand getPriceCommand) {
        super(subscriberService);
        this.getPriceCommand = getPriceCommand;
    }

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long userIdInBot = message.getChatId();
        Double priceForSubcription = getPriceForSubcription(message);

        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        try {
            subscriberService.updatePriceSubscription(userIdInBot, priceForSubcription);
        } catch (NotFoundException e) {
            answer.setText(e.getMessage());
            execute(absSender, answer);
            return;
        }
        answer.setText("Новая подписка создана на стоимость " + priceForSubcription + " USD");

        sendCurrentPrice(absSender, message, arguments);
        execute(absSender, answer);
        log.info("Создание новой подписки: {} для пользователя: {}", priceForSubcription, priceForSubcription);
    }

    private void sendCurrentPrice(AbsSender absSender, Message message, String[] arguments) {
        getPriceCommand.processMessage(absSender, message, arguments);
    }

    private Double getPriceForSubcription(Message message) {
        return Double.valueOf(
                message.getText()
                        .replaceAll("\\D", "")
                        .replaceAll("\\s", "")
                        .trim());
    }

}