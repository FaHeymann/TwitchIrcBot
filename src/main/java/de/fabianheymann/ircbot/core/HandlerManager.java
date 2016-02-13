package de.fabianheymann.ircbot.core;

import de.fabianheymann.ircbot.handlers.BasicHandler;
import de.fabianheymann.ircbot.handlers.IHandler;
import de.fabianheymann.ircbot.handlers.hearthstone.HearthstoneHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

class HandlerManager implements Runnable {

    private ConcurrentLinkedQueue<String> inputQueue;

    private ConcurrentLinkedQueue<String> outputQueue;

    private List<IHandler> handlers;

    public HandlerManager(ConcurrentLinkedQueue<String> inputQueue, ConcurrentLinkedQueue<String> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.handlers = this.getHandlers();
    }

    @Override
    public void run() {

        while(true) {
            if(!this.inputQueue.isEmpty()) {

                String message = this.inputQueue.poll();

                System.out.println("Handler received: " + message);

                String response = this.handle(message);

                System.out.println("Handler returned: " + response);

                if(!message.equals("")) {
                    this.outputQueue.add(response);
                }
            }
            try {
                Thread.sleep(200L);
            } catch(InterruptedException IE) {
                IE.printStackTrace();
            }
        }
    }


    public String handle(String message) {
        for(IHandler handler : this.handlers) {
            String response = handler.handle(message);
            if(null != response && !response.equals("")) {
                return response;
            }
        }
        return "";
    }

    public List<IHandler> getHandlers()
    {
        List<IHandler> handlers = new ArrayList<>();
        handlers.add(new BasicHandler());
        handlers.add(new HearthstoneHandler());
        return handlers;
    }
}
