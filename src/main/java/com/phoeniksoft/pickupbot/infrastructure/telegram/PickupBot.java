package com.phoeniksoft.pickupbot.infrastructure.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
public class PickupBot extends TelegramLongPollingBot {

    @Value("${telegram.name}")
    private String botUsername;

    @Value("${telegram.token}")
    private String botToken;

    private TelegramFacade telegramFacade;

    public PickupBot(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @PostConstruct
    private void startBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            log.error("Telegram request error", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<SendMessage> sendMessage = telegramFacade.handleMessage(update);
        if (sendMessage.isPresent()) {
            try {
                execute(sendMessage.get());
            } catch (TelegramApiException e) {
                log.error("Telegram api error", e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
