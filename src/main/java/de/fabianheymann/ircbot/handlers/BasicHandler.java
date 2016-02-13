package de.fabianheymann.ircbot.handlers;

public class BasicHandler implements IHandler {

    @Override
    public String handle(String message) {
        if(message.equals("!bot")) {
            return "Hello I am your twitch bot";
        }
        return "";
    }
}
