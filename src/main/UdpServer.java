package main;

import Threads.ThreadForExit;
import command.*;
import human.HumanBeing;


import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



public class UdpServer implements Runnable {
    private DatagramSocket socket;
    public static Logger log = LogManager.getLogger();
    //Application app;
    public static boolean flag = false;
    private ThreadForExit threadForExit = new ThreadForExit();
    public static StringBuilder ans = new StringBuilder();

    public UdpServer(Application app, ArrayDeque<HumanBeing> Humanity) throws SocketException {
        socket = new DatagramSocket(8080);
        //this.app = app;
    }

    @Override
    public void run() {
        log.info("Сервер запущен");
        Thread thread = new Thread(threadForExit);
        thread.start();
        while (true) {
            // Фомирование пакета для приема
            ByteBuffer buffer = ByteBuffer.allocate(65507);
            DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.limit());
            try {
                if (flag)
                    break;
                else {
                    socket.receive(packet);
                    log.info("Пакет принят.");
                }
                Command command = (Command) Serializer.deserialize(packet.getData());
                Application.Request(command);
                // Формирование ответа
                buffer.put(new byte[65507]);
                buffer.flip();
                //ans.append("Команда ").append(command.getNameOfCommand()).append(" выполнена");
                buffer.put(Serializer.serialize(ans));

                // Формирование обратного пакета
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buffer.array(), buffer.limit(), address, port);
                // Попытка отправки
                if (flag)
                    break;
                else {
                    socket.send(packet);
                    log.info("Пакет отправлен.");
                }
                ans = new StringBuilder();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
