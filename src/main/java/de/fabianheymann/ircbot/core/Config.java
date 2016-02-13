package de.fabianheymann.ircbot.core;

import java.io.*;
import java.net.URL;


class Config {
    static final int SERVER = 0;
    static final int PORT = 1;
    static final int USER = 2;
    static final int PW = 3;
    static final int CHANNEL = 4;
    
    private String server;
    private String port;
    private String user;
    private String pw;
    private String channel;
    
    public Config() {
        this.server = "";
        this.port = "";
        this.user = "";
        this.pw = "";
        this.channel = "";
        this.readFromFile();
    }
    
    public void readFromFile() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Config.class.getResourceAsStream("/config/config.conf")));
            String line;
            while((line = br.readLine()) != null) { //read config
                if(line.matches(".+:.+")) {
                    String varName = line.substring(0, line.indexOf(":")).trim();
                    String value = line.substring(line.indexOf(":") + 1).trim();
                    
                    if(varName.equals("server")) {
                        this.server = value;
                    }
                    if(varName.equals("port")) {
                        this.port = value;
                    }
                    if(varName.equals("user")) {
                        this.user = value;
                    }
                    if(varName.equals("password")) {
                        this.pw = value;
                    }
                    if(varName.equals("channel")) {
                        this.channel = value;
                    }
                }
            }
            br.close();
        } catch(FileNotFoundException fnfe) {
            System.out.println("No config file found");
            System.exit(0);
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
    }
    
    public String[] getConnection() {
        if(this.server.equals("") || this.port.equals("") || this.user.equals("") || this.pw.equals("") || this.channel.equals("") || !this.port.matches("-?\\d+(\\.\\d+)?")) {
            return null;
        }
        return new String[]{this.server, this.port, this.user, this.pw, this.channel};
    }

}
