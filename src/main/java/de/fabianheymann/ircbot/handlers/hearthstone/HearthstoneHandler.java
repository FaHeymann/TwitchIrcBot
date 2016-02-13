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
    
//    public void readFromFile(String file) {
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while((line = br.readLine()) != null) { //read commands
//                if(line.matches(".+:.+")) {
//                    String varName = line.substring(0, line.indexOf(":")).trim();
//                    String value = line.substring(line.indexOf(":") + 1).trim();
//                    if(varName.length() > 3) {
//                        this.commands.put(varName, value);
//                    }
//                }
//            }
//            br.close();
//        } catch(FileNotFoundException fnfe) {
//            System.out.println("No commands file found");
//            System.exit(0);
//        } catch (IOException IOE) {
//            IOE.printStackTrace();
//        }
//    }
    
//    public String respond(String line) {
//        String response = "";
//
//        if(line.contains("PRIVMSG")) {
//
//            String[] split = line.split(":");
//            String sender = line.substring(line.indexOf(":") + 1, line.indexOf("!"));
//            String message = split[split.length - 1];
//
//            if(message.startsWith("!card ")) {
//                message = message.substring(6);
//
//                System.out.println("Search: " + message + "\nRequest by: " + sender);
//                if(message.length() > 3) {
//                    response = WowHead.processMap(WowHead.search(message, this.cardMaps));
//                } else {
//                    response = "Card name too short. (< 4)";
//                }
//            } else {
//                for(String varName : commands.keySet()) {
//                    if(message.toLowerCase().equals("!" + varName.toLowerCase())) {
//                        response = commands.get(varName);
//                    }
//                }
//            }
//        } else {
//            if(line.contains("Login unsuccessful")) {
//                System.out.println("IRC Login failed");
//                System.exit(0);
//            }
//            if(line.contains("connected")) {
//                System.out.println("IRC Connection established");
//            }
//        }
//        return response;
//    }

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
