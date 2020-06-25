package main;

import Threads.ThreadForExit;
import command.*;
import human.HumanBeing;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import jdbc.Registration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



public class UdpServer {
    private DatagramSocket socket;
    private Command command;
    public static Logger log = LogManager.getLogger();
    private DatagramChannel server;
    private InetSocketAddress serverAddr;
    //public static StringBuilder ans = new StringBuilder();
    private Selector selector;
    private SocketAddress remoteAdd;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public void run() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            Selector selector = Selector.open();
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress iAdd = new InetSocketAddress("localhost", 8080);
            channel.bind(iAdd);
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            log.info("Сервер запущен");
            while (true) {
                if (in.ready()) {
                    String serverCommand = in.readLine();
                    if (serverCommand.equals("end")) {
                        System.out.println("Сервер завершил работу");
                        System.exit(0);
                    }
                    if (serverCommand.equals("save")){
                        //System.out.println("sss");
                        String[] args = new String[5];
                        Command command = new Command("exit", args);
                        Application.Request(command);
                    }
                }

                selector.selectNow();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isReadable()) {
                        //read(key);
                        command = (Command) forkJoinPool.invoke(new ConnectionHandler(selector, key));
                        System.out.println(command.getNameOfCommand());
                        //System.out.println("***");
                        executorService.submit(new RequestHandler(command)).get();

                        //System.out.println(ans);

                    }
                    if (key.isWritable()) {
                        //System.out.println("|||");
                        //write(key);
                        new Thread(new SendingHandler(key, command)).start();
                        key.interestOps(SelectionKey.OP_READ);
                    }
                    //System.out.println("*");

                    iter.remove();

                    //System.out.println(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*void read(SelectionKey key) {
        ByteBuffer output = ByteBuffer.allocate(65507);
        DatagramChannel client = (DatagramChannel) key.channel();
        try {
            remoteAdd = client.receive(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Command command = null;
        try {
            command = (Command) Serializer.deserialize(output.array());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println(command.getNameOfCommand());
        Application.Request(command);
        //System.out.println(ans);
        key.interestOps(SelectionKey.OP_WRITE);

    }

     */
    /*void write(SelectionKey key) {
        //System.out.println("5");
        ByteBuffer buffer = ByteBuffer.allocate(65507);
        buffer.put(new byte[65507]);
        buffer.flip();
        //ans.append("Команда ").append(command.getNameOfCommand()).append(" выполнена");
        try {
            System.out.println(ans + "55445345");
            //Registration.showMap();
            buffer.put(Serializer.serialize(ans));
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip();
        DatagramChannel client = (DatagramChannel) key.channel();
        try {
            client.send(buffer, ConnectionHandler.remoteAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        key.interestOps(SelectionKey.OP_READ);
        ans = new StringBuilder();
    }

     */
}
