package com.phoeniksoft.pickupbot.infrastructure.telegram;

public interface TelegramConstants {

    String START_COMMAND = "/start";
    String GET_ADVICE_COMMAND = "Получить совет \uD83D\uDD25";
    String GOOD_ADVICE_COMMAND = "Одобряю \uD83D\uDC4D";
    String BAD_ADVICE_COMMAND = "Так себе \uD83D\uDC4E";

    String GREETING_MSG = "Привет!\n\n Я Пикап Бот и моя миссия - упростить процесс знакомства с девушками в интернете. Всегда всё в твоих руках, просто я дам тебе пару подсказок. Давай начнём! \uD83D\uDE80";
    String ALL_MESSAGES_SHOWN_MSG = "Кажется это пока всё, что у меня есть. Сейчас советы будут повторяться, но жди обновлений \uD83D\uDDE3";
    String GOOD_ADVICE_ANSWER_MSG = "Круто! Дальше всё в твоих руках \u270A";
    String BAD_ADVICE_ANSWER_MSG = "Окей, я мог бы и лучше постараться \uD83D\uDE2C";
    String UNRECOGNIZABLE_USER_ANSWER_ERROR = "Эта команда мне неизвестна \uD83E\uDD16";
    String UNEXPECTED_ERROR = "Возникла ошибка, попробуй ещё раз \uD83D\uDD27";
}
