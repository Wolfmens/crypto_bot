package com.skillbox.cryptobot.bot;

import com.skillbox.cryptobot.enam.CommandBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CryptoBot extends TelegramLongPollingCommandBot {

    private final String botUsername;

    public CryptoBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            List<IBotCommand> commandList
    ) {
        super(botToken);
        this.botUsername = botUsername;

        commandList.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText("Выбрана не существующая команда. Выберете из следующих: \n" +
                Arrays.stream(CommandBot.values())
                        .map(commandBot -> commandBot.getCommand() + " -> " + commandBot.getDescription())
                        .collect(Collectors.joining("\n")));
        sendMessageToUser(message);
        log.info("Выбрана не существующая команда: {}", update.getMessage().getText());
    }

    public void sendNoticeSubscriptionPriceUsers(String chatId, String currentPrice) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Пора покупать, стоимость биткоина: " + currentPrice);

        sendMessageToUser(message);
    }

    private void sendMessageToUser(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения уведомления: {}", e.getMessage());
        }
    }
}
