package de.fabianheymann.ircbot.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;


class Listener implements Runnable {
    
    private ConcurrentLinkedQueue<String> queue;
    private IRCConnection con;
    
    Listener(ConcurrentLinkedQueue<String> queue, IRCConnection con) {
        this.queue = queue;
        this.con = con;
    }

    @Override
    public void run() {
        BufferedReader br = this.con.getBR();
        
        String line;
        try {
            while((line = br.readLine()) != null) {
                System.out.println("Reader received: " + line);

                String[] segments = line.split(":");
                line = "";
                for(int i = 0; i < segments.length; i++) {
                    if(i < 2) {
                        continue;
                    }
                    line += segments[i];
                }

                this.queue.add(line);
                try {
                    Thread.sleep(200L);
                } catch(InterruptedException IE) {
                    
                }
            }
        } catch(IOException IOE) {
            IOE.printStackTrace();
        }
    }

}
