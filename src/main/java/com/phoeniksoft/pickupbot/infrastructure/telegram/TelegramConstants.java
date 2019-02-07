package com.phoeniksoft.pickupbot.infrastructure.telegram;

public interface TelegramConstants {

    String START_COMMAND = "/start";
    String GET_ADVICE_COMMAND = "Получить совет \u1F525";
    String GOOD_ADVICE_COMMAND = "Одобряю \u1F44D";
    String BAD_ADVICE_COMMAND = "Так себе \u1F44E";

    String GREETING_MSG = "Привет!\n\n Я Пикап Бот и моя миссия - упростить процесс знакомства с девушками в интернете. Всегда всё в твоих руках, просто я дам тебе пару подсказок. Давай начнём! \u1F680";
    String ALL_MESSAGES_SHOWN_MSG = "Кажется это пока всё, что у меня есть. Сейчас советы будут повторяться, но жди обновлений \u1F5E3";
    String GOOD_ADVICE_ANSWER_MSG = "Круто! Дальше всё в твоих руках \u270A";
    String BAD_ADVICE_ANSWER_MSG = "Окей, я мог бы и лучше постараться \u1F62C";
    String UNRECOGNIZABLE_USER_ANSWER_ERROR = "Эта команда мне неизвестна \u1F916";
    String UNEXPECTED_ERROR = "Возникла ошибка, попробуй ещё раз \u1F527";
}
