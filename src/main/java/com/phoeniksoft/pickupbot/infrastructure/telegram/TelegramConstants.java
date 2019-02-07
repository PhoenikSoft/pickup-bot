package com.phoeniksoft.pickupbot.infrastructure.telegram;

public interface TelegramConstants {

    String START_COMMAND = "/start";
    String GET_ADVICE_COMMAND = "Получить совет U+1F525";
    String GOOD_ADVICE_COMMAND = "Одобряю U+1F44D";
    String BAD_ADVICE_COMMAND = "Так себе U+1F44E";

    String GREETING_MSG = "Привет!\n\n Я Пикап Бот и моя миссия - упростить процесс знакомства с девушками в интернете. Всегда всё в твоих руках, просто я дам тебе пару подсказок. Давай начнём! U+1F680";
    String ALL_MESSAGES_SHOWN_MSG = "Кажется это пока всё, что у меня есть. Сейчас советы будут повторяться, но жди обновлений U+1F5E3";
    String GOOD_ADVICE_ANSWER_MSG = "Круто! Дальше всё в твоих руках U+270A";
    String BAD_ADVICE_ANSWER_MSG = "Окей, я мог бы и лучше постараться U+1F62C";
    String UNRECOGNIZABLE_USER_ANSWER_ERROR = "Эта команда мне неизвестна U+1F916";
    String UNEXPECTED_ERROR = "Возникла ошибка, попробуй ещё раз U+1F527";
}
