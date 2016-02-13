package de.fabianheymann.ircbot.handlers.hearthstone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class WowHead {

    private List<Map<String, String>> cards;

    public WowHead() {
        this.cards = this.getAllCards();
    }
    
    public String search(String search) {
        List<Map<String, String>> found = new ArrayList<>();
        for(Map<String, String> card : this.cards) {
            if(card.containsKey("name") && card.get("name").toLowerCase().contains(search.toLowerCase())) {
                found.add(card);
            }
        }
        return this.processMap(found);
    }
    
    private List<Map<String, String>> getAllCards() {
        try {
            ArrayList<Map<String, String >> cardMaps = new ArrayList<>();
            
            String result = HTTPConnection.sendRequest("http://www.wowhead.com/hearthstone/cards", "");
            String[] lines = result.split("\n");
            for(String line : lines) {
                if(line.contains("var hearthstoneCards")) {
                    result = line;
                }
            }
            result = result.substring(result.indexOf("[") + 2, result.indexOf("]") - 1);
            String[] cards = result.split("\\},\\{");
            for(String card : cards) {
                HashMap<String, String> current = new HashMap<>();
                String[] attributes = card.split(",(?!\\s)");
                for(String attribute : attributes) {
                    if(attribute.contains(":")) {
                        current.put(attribute.substring(0, attribute.indexOf(':')).replace("\"", ""), 
                                attribute.substring(attribute.indexOf(':') + 1, attribute.length()).replace("\"", ""));
                    }
                }
                cardMaps.add(current);
            }
            return cardMaps;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    private String processMap(List<Map<String,String>> cards) {
        
        Map<String, String> type = new HashMap<>(5);
        type.put("5", "Ability");
        type.put("3", "Hero");
        type.put("4", "Minion");
        type.put("7", "Weapon");
        type.put("10", "Hero Power");
        
        Map<String, String> set = new HashMap<>(4);
        set.put("2", "Basic");
        set.put("3", "Expert");
        set.put("4", "Reward");
        set.put("5", "Mission");
        
        Map<String, String> classs = new HashMap<>(9);
        classs.put("1", "Warrior");
        classs.put("2", "Paladin");
        classs.put("3", "Hunter");
        classs.put("4", "Rogue");
        classs.put("5", "Priest");
        classs.put("7", "Shaman");
        classs.put("8", "Mage");
        classs.put("9", "Warlock");
        classs.put("11", "Druid");
        
        Map<String, String> quality = new HashMap<>(5);
        quality.put("0", "Free");
        quality.put("1", "Common");
        quality.put("2", "Token");
        quality.put("3", "Rare");
        quality.put("4", "Epic");
        quality.put("5", "Legendary");
        
        Map<String, String> race = new HashMap<>(6);
        race.put("14", "Murloc");
        race.put("15", "Demon");
        race.put("20", "Beast");
        race.put("21", "Totem");
        race.put("23", "Pirate");
        race.put("24", "Dragon");
        
        if(cards == null) {
            return "No result\n\r";
        }
        
        String output = "";
        
        for(Map<String, String> card : cards) {
            output += "[";
            if(card.containsKey("name")) {
                output += "[" + card.get("name");
                if(card.containsKey("description")) {
                    output += ": " + card.get("description");
                }
                output += "]";
            }
            
            if(card.containsKey("type")) {
                output += "[" + type.get(card.get("type")) + "]";
            }
            
            if(card.containsKey("cost") || card.containsKey("attack") || card.containsKey("health") || card.containsKey("durability")) {
                output += "[";
                if(card.containsKey("cost")) {
                    output += "Cost: " + card.get("cost") + ", ";
                }
                if(card.containsKey("attack")) {
                    output += "ATK: " + card.get("attack") + ", ";
                }
                if(card.containsKey("health")) {
                    output += "HP: " + card.get("health") + ", ";
                }
                if(card.containsKey("durability")) {
                    output += "Uses: " + card.get("durability") + ", ";
                }
                output = output.substring(0, output.length() -2);
                output += "]";
            }
            
            if(card.containsKey("classs")) {
                output += "[" + classs.get(card.get("classs")) + "]";
            }
            
            if(card.containsKey("race")) {
                output += "[" + race.get(card.get("race")) + "]";
            }
            
            if(card.containsKey("quality") || card.containsKey("set")) {
                output += "[";
                boolean hasSet = false;
                if(card.containsKey("set")) {
                    output += set.get(card.get("set"));
                    hasSet = true;
                }
                if(card.containsKey("quality")) {
                    if(hasSet) {
                        output += "/";
                    }
                    output += quality.get(card.get("quality"));
                }
                output += "]";
            }
            output += "]";
        }
        return output;
    }

}
