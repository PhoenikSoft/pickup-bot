package com.phoeniksoft.pickupbot.infrastructure.telegram;

public interface TelegramConstants {

    String START_COMMAND = "/start";
    String GET_ADVICE_COMMAND = "Получить совет \uD83D\uDD25";
    String GOOD_ADVICE_COMMAND = "Одобряю \uD83D\uDC4D";
    String BAD_ADVICE_COMMAND = "Так себе \uD83D\uDC4E";
    String SUBSCRIBE_COMMAND = "Подписаться";
    String GET_MESSAGE_ADVICE_COMMAND = "Вариант первого сообщения \u2709";
    String GET_DATE_ADVICE_COMMAND = "Вариант для встречи \u2615";
    String GET_PROFILE_ADVICE_COMMAND = "Совет по профилю \uD83E\uDD35";
    String RETURN_TO_MAIN_MENU_COMMAND = "Вернуться в главное меню \u21A9";

    String GREETING_MSG = "Привет!\n\n Я Пикап Бот и моя миссия - упростить процесс знакомства с девушками в интернете. Всегда всё в твоих руках, просто я дам тебе пару подсказок. Давай начнём! \uD83D\uDE80";
    String ALL_MESSAGES_SHOWN_MSG = "Кажется это пока всё, что у меня есть. Сейчас советы будут повторяться, но жди обновлений \uD83D\uDDE3";
    String GOOD_ADVICE_ANSWER_MSG = "Круто! Дальше всё в твоих руках \u270A";
    String BAD_ADVICE_ANSWER_MSG = "Окей, я мог бы и лучше постараться \uD83D\uDE2C";
    String UNRECOGNIZABLE_USER_ANSWER_ERROR = "Эта команда мне неизвестна \uD83E\uDD16";
    String UNEXPECTED_ERROR = "Возникла ошибка, попробуй ещё раз \uD83D\uDD27";
    String CHOOSE_OPTION_MSG = "Выбери следующее действие \u2B07";
    String THANKS_FOR_FEEDBACK_MSG = "Спасибо за ответ!";
    String SUBSCRIBE_MSG = "Если хочешь получать уведомления на тему \"%s\", нажми на кнопку ниже. Подпишись по братски :)";

    String MESSAGES_LABEL = "Первые сообщения";
    String DATE_LABEL = "Свидание";
    String PROFILE_LABEL = "Профиль";
}
