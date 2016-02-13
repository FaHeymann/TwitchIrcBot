package de.fabianheymann.ircbot.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

class Writer implements Runnable {
    
    private ConcurrentLinkedQueue<String> queue;
    private IRCConnection connection;
    
    Writer(ConcurrentLinkedQueue<String> queue, IRCConnection connection) {
        this.queue = queue;
        this.connection = connection;
    }

    @Override
    public void run() {
        
        BufferedWriter bw = this.connection.getBW();
        
        while(true) {
            if(!this.queue.isEmpty()) {
                
                String response = this.queue.poll();
                System.out.println("Writer: " + response);
                
                if(!response.equals("")) {
                    try {
                        bw.write("PRIVMSG #drombart :" + response + "\n\r");
                        bw.flush();
                    } catch(IOException IOE) {
                        IOE.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(200L);
            } catch(InterruptedException IE) {
                IE.printStackTrace();
            }
        }
    }
}
