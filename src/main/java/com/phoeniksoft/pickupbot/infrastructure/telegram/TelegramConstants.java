package com.phoeniksoft.pickupbot.infrastructure.telegram;

public interface TelegramConstants {

    String START_COMMAND = "/start";
    String GET_MESSAGE_ADVICE_COMMAND = "Get message advice";
    String GET_DATE_ADVICE_COMMAND = "Get date advice";
    String GET_PROFILE_ADVICE_COMMAND = "Get profile advice";
    String RETURN_TO_MAIN_MENU_COMMAND = "Return to main menu";
    String GOOD_ADVICE_COMMAND = "Good advice";
    String BAD_ADVICE_COMMAND = "Bad advice";

    String GREETING_MSG = "Welcome to the Pickup Bot!\nThis bot can give you some messages that you can use during your conversations with girls.\nJust push the button below and we can start!";
    String CHOOSE_OPTION_MSG = "Please choose some option";
    String ALL_MESSAGES_SHOWN_MSG = "We have shown you all our messages :(\nPlease wait for our update...";
    String GOOD_ADVICE_ANSWER_MSG = "Yeah, we have cooler advice";
    String BAD_ADVICE_ANSWER_MSG = "Ok, we could do it better";
    String THANKS_FOR_FEEDBACK_MSG = "Thanks for your feedback!";
    String UNRECOGNIZABLE_USER_ANSWER_ERROR = "Cannot analyze your answer. Please choose some option from the list below.";
    String UNEXPECTED_ERROR = "Some error occurred. Please try again.";
}
