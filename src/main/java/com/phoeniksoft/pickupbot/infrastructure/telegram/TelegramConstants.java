package com.phoeniksoft.pickupbot.infrastructure.telegram;

public interface TelegramConstants {

    String START_COMMAND = "/start";
    String GET_ADVICE_COMMAND = "Get advice";
    String GOOD_ADVICE_COMMAND = "Good advice";
    String BAD_ADVICE_COMMAND = "Bad advice";

    String GREETING_MSG = "Welcome to the Pickup Bot!\nThis bot can give you some messages that you can use during your conversations with girls.\nJust push the button below and we can start!";
    String ALL_MESSAGES_SHOWN_MSG = "We have shown you all our messages :(\nPlease wait for our update...";
    String GOOD_ADVICE_ANSWER_MSG = "Yeah, we have cooler advice";
    String BAD_ADVICE_ANSWER_MSG = "Ok, we could do it better";
    String UNRECOGNIZABLE_USER_ANSWER_ERROR = "Cannot analyze your answer. Please choose some option from the list below.";
    String UNEXPECTED_ERROR = "Some error occurred. Please try again.";
}
