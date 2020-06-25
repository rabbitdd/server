package main;

import command.Command;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;



public class SendingHandler implements Runnable {
    SelectionKey key;
    Command command;
    public SendingHandler(SelectionKey key, Command command) {
        this.key = key;
        this.command = command;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        ByteBuffer buffer = ByteBuffer.allocate(65507);
        buffer.put(new byte[65507]);
        buffer.flip();
        //ans.append("Команда ").append(command.getNameOfCommand()).append(" выполнена");
        try {
            System.out.println("Отправка ответа на команду " + command.getNameOfCommand() + " Пользователь: " + command.getUser().getLogin());
            System.out.println(command.getAns());
            System.out.println("----------------------------------");
            //Registration.showMap();
            buffer.put(Serializer.serialize(command.getAns()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip();
        DatagramChannel client = (DatagramChannel) key.channel();
        try {
            client.send(buffer, command.getRemoteAdd());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ans = new StringBuilder();
       // key.interestOps(SelectionKey.OP_READ);
    }
}
