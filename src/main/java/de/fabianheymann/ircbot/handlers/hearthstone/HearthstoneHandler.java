package de.fabianheymann.ircbot.handlers.hearthstone;

import de.fabianheymann.ircbot.handlers.IHandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class HearthstoneHandler implements IHandler {
    
    private HashMap<String, String> commands;
    private WowHead wowHead;
    
    public HearthstoneHandler() {
        this.commands = new HashMap<>();
        this.wowHead = new WowHead();
    }

    @Override
    public String handle(String message) {
        String response = "";
        if(message.startsWith("!card ")) {
            message = message.substring(6);

            System.out.println("Search: " + message);
            if(message.length() > 3) {
                response = this.wowHead.search(message);
            } else {
                response = "Card name too short. (< 4)";
            }
        } else {
            for(String varName : commands.keySet()) {
                if(message.toLowerCase().equals("!" + varName.toLowerCase())) {
                    response = commands.get(varName);
                }
            }
        }
        return response;
    }
}
