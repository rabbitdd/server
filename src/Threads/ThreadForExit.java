package Threads;

import main.UdpServer;

import java.util.Scanner;

public class ThreadForExit implements Runnable{
    public void run() {
        Scanner in = new Scanner(System.in);
        String msg = in.next();
        //System.out.println("поток 2");
        if (msg.equals("end")) {
            UdpServer.log.info("Завершение работы");
            System.exit(0);
        }

    }
}
