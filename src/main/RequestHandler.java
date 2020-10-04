package main;

import command.Command;

public class RequestHandler implements Runnable {
    private Command command;

    public RequestHandler(Command command) {
        this.command = command;
    }

    @Override
    public void run() {
        //command.getHuman().setUsername(command.getUser().getUserName());
        //UdpServer.ans = new StringBuilder();
        Application.Request(command);
        System.out.println(Thread.currentThread().getName());
    }
}
