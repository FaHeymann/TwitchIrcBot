package de.fabianheymann.ircbot.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;


class IRCConnection {
    
    private boolean connected = false;
    
    private Socket socket;
    
    private BufferedReader br;
    private BufferedWriter bw;
    
    private String server;
    private int port;
    private String user;
    private String oAuth;
    private String channel;
    
    IRCConnection(String server, int port, String user, String oAuth, String channel, boolean connect)
    {
        if(!(channel.charAt(0) == '#')) {
            channel = "#" + channel;
        }
        this.server = server;
        this.port = port;
        this.user = user;
        this.oAuth = oAuth;
        this.channel = channel;
        
        if(connect) {
            this.connect();
        }
    }
    
    IRCConnection(String server, int port, String user, String oAuth, String channel) {
        this(server, port, user, oAuth, channel, true);
    }
    
    IRCConnection(Config conf, boolean connect) {
        String[] conData = conf.getConnection();
        if(conData == null) {
            System.out.println("Error in config file");
            System.exit(0);
        }
        
        if(!(conData[Config.CHANNEL].charAt(0) == '#')) {
            conData[Config.CHANNEL] = "#" + conData[Config.CHANNEL];
        }
        this.server = conData[Config.SERVER];
        this.port = Integer.parseInt(conData[Config.PORT]);
        this.user = conData[Config.USER];
        this.oAuth = conData[Config.PW];
        this.channel = conData[Config.CHANNEL];
        
        if(connect) {
            this.connect();
        }
    }
    
    IRCConnection(Config conf) {
        this(conf, true);
    }
    
    public String getChannel() {
        return this.channel;
    }
    
    public BufferedReader getBR() {
        if(this.connected) {
            return this.br;
        }
        return null;
    }
    
    public BufferedWriter getBW() {
        if(this.connected) {
            return this.bw;
        }
        return null;
    }
    
    public void connect() {
        try {
            this.socket = new Socket(this.server, this.port);
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            
            this.bw.write("PASS oauth:" + this.oAuth + "\n\r");
            this.bw.write("NICK " + this.user + "\n\r");
            this.bw.write("JOIN " + this.channel + "\n\r");
            
            this.bw.flush();
            
            this.connected = true;
            
        } catch(UnknownHostException UHE) {
            System.out.println("Server not found");
            System.exit(0);
        } catch(IOException IOE) {
            IOE.printStackTrace();
        }
    }
    
    public void disconnect() {
        if(this.connected) {
            try {
                this.socket.close();
            } catch(IOException IOE) {
                IOE.printStackTrace();
            }
        }
    }

}
