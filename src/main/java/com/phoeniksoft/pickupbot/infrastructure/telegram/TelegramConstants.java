package com.phoeniksoft.pickupbot.infrastructure.telegram;

public interface TelegramConstants {

    String START_COMMAND = "/start";
    String GET_ADVICE_COMMAND = "Получить совет \uD83D\uDD25";
    String GOOD_ADVICE_COMMAND = "Одобряю \uD83D\uDC4D";
    String BAD_ADVICE_COMMAND = "Так себе \uD83D\uDC4E";
    String GET_MESSAGE_ADVICE_COMMAND = "Get message advice";
    String GET_DATE_ADVICE_COMMAND = "Get date advice";
    String GET_PROFILE_ADVICE_COMMAND = "Get profile advice";
    String RETURN_TO_MAIN_MENU_COMMAND = "Return to main menu";
    String GOOD_ADVICE_COMMAND = "Good advice";
    String BAD_ADVICE_COMMAND = "Bad advice";

    String GREETING_MSG = "Привет!\n\n Я Пикап Бот и моя миссия - упростить процесс знакомства с девушками в интернете. Всегда всё в твоих руках, просто я дам тебе пару подсказок. Давай начнём! \uD83D\uDE80";
    String ALL_MESSAGES_SHOWN_MSG = "Кажется это пока всё, что у меня есть. Сейчас советы будут повторяться, но жди обновлений \uD83D\uDDE3";
    String GOOD_ADVICE_ANSWER_MSG = "Круто! Дальше всё в твоих руках \u270A";
    String BAD_ADVICE_ANSWER_MSG = "Окей, я мог бы и лучше постараться \uD83D\uDE2C";
    String UNRECOGNIZABLE_USER_ANSWER_ERROR = "Эта команда мне неизвестна \uD83E\uDD16";
    String UNEXPECTED_ERROR = "Возникла ошибка, попробуй ещё раз \uD83D\uDD27";
    String GREETING_MSG = "Welcome to the Pickup Bot!\nThis bot can give you some messages that you can use during your conversations with girls.\nJust push the button below and we can start!";
    String CHOOSE_OPTION_MSG = "Please choose some option";
    String ALL_MESSAGES_SHOWN_MSG = "We have shown you all our messages :(\nPlease wait for our update...";
    String GOOD_ADVICE_ANSWER_MSG = "Yeah, we have cooler advice";
    String BAD_ADVICE_ANSWER_MSG = "Ok, we could do it better";
    String THANKS_FOR_FEEDBACK_MSG = "Thanks for your feedback!";
    String UNRECOGNIZABLE_USER_ANSWER_ERROR = "Cannot analyze your answer. Please choose some option from the list below.";
    String UNEXPECTED_ERROR = "Some error occurred. Please try again.";
}
