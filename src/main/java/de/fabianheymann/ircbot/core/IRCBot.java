package de.fabianheymann.ircbot.core;

import java.util.concurrent.ConcurrentLinkedQueue;


public class IRCBot
{
    private Config config;

    private IRCConnection connection;

    private ConcurrentLinkedQueue<String> inputQueue;

    private ConcurrentLinkedQueue<String> outputQueue;

    private Thread listener;

    private Thread writer;

    private Thread handler;

    public IRCBot()
    {
        this.config = new Config();

        this.connection = new IRCConnection(this.config);

        this.outputQueue = new ConcurrentLinkedQueue<>();
        this.inputQueue = new ConcurrentLinkedQueue<>();

        this.listener = new Thread(new Listener(this.inputQueue, this.connection));
        this.handler = new Thread(new HandlerManager(this.inputQueue, this.outputQueue));
        this.writer = new Thread(new Writer(this.outputQueue, this.connection));

        this.listener.start();
        this.handler.start();
        this.writer.start();

        try {
            this.listener.join();
            this.handler.join();
            this.writer.join();
        } catch(InterruptedException IE) {
            // do nothing
        }
    }

    public static void main(String[] args)
    {
        IRCBot ircBot = new IRCBot();
    }

}
