package Exercise4;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatRoom {

    private static final String TERMINATE = "Exit";
    static String name;
    static boolean done = false;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Two arguments used to connect: <class D host IP> <UDP port number>");
        } else {
            try {
                //InetAddress is used for Internet Protocol addresses.Value will be that of the first command line argument
                InetAddress group = InetAddress.getByName(args[0]);
                //Port number is needed for a socket connection.Therefore,the second argument will define the port number
                int port = Integer.parseInt(args[1]);
                //Scanner reads what each user types
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your name: ");
                name = scanner.nextLine();

                //Multicast sockets send and receive IP multicast packets.
                MulticastSocket socket = new MulticastSocket(port);
                socket.setTimeToLive(0);
                socket.joinGroup(group);

                //Thread created based on the TalkThread 
                Thread thread = new Thread(new TalkThread(socket, group, port));

                thread.start();

                System.out.println("Start chatting...\n");
                while (true) {
                    String message;
                    message = scanner.nextLine();
                    if (message.equalsIgnoreCase(ChatRoom.TERMINATE)) {
                        done = true;
                        socket.leaveGroup(group);
                        socket.close();
                        break;
                    }

                    message = name + ": " + message;
                    byte[] buffer = message.getBytes();
                    DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, port);
                    socket.send(datagram);
                }
            } catch (SocketException se) {
                System.out.println("Socket Creation Error!!");
                se.printStackTrace();
            } catch (IOException ie) {
                System.out.println("Reading or writing socket Error!!");
                ie.printStackTrace();
            }
        }
    }
}

class TalkThread implements Runnable {

    private MulticastSocket socket;
    private InetAddress group;
    private int port;

    TalkThread(MulticastSocket socket, InetAddress group, int port) {
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    @Override
    public void run() {
        while (!ChatRoom.done) {
            byte[] buffer = new byte[1000];
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, port);

            String message;

            try {
                socket.receive(datagram);
                message = new String(buffer, 0, datagram.getLength(), "UTF-8");

                if (!message.startsWith(ChatRoom.name)) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Socket is closed!!");
            }
        }
    }
}
