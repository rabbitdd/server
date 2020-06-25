package main;

import command.Command;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ConnectionHandler extends RecursiveTask {
    private Selector selector;
    private SelectionKey selectionKey;
    public SocketAddress remoteAdd;
    private Command command;

    public ConnectionHandler(Selector selector, SelectionKey key) {
        this.selector = selector;
        this.selectionKey = key;
    }
    @Override
    protected Command compute() {
        System.out.println(Thread.currentThread().getName());
        DatagramChannel client = (DatagramChannel) selectionKey.channel();
        UdpServer.log.info("Произошло подключение");
        return read(client);

    }

    Command read(DatagramChannel client) {
        ByteBuffer output = ByteBuffer.allocate(65507);
        try {
            remoteAdd = client.receive(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        command = null;
        try {
            command = (Command) Serializer.deserialize(output.array());
            command.setRemoteAdd(remoteAdd);
            System.out.println("Команда отправлена от " + command.getUser().getLogin());
            System.out.println("---------------------------------");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        System.out.println("*");
        //System.exit(0);
        return command;

    }
}
