package Threads;

import command.Command;
import main.Application;
import main.UdpServer;

import java.util.Scanner;

public class ThreadForExit implements Runnable{
    public void run() {
        while (true) {
            Scanner in = new Scanner(System.in);
            String msg = in.next();
            //System.out.println("поток 2");
            if (msg.equals("end")) {
                UdpServer.log.info("Завершение работы");
                System.exit(0);
            } else if (msg.equals("save")) {
                String[] args = new String[5];
                Command command = new Command("exit", args);
                Application.Request(command);
            } else {
                System.out.println("Такую команду сервер не поддерживает");
            }
        }

    }
}
