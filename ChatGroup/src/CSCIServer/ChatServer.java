package CSCIServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by albertoprego on 10/20/15.
 */
public class ChatServer {
    private Vector<ChatThread> ctVector;
    public ChatServer(int port){
        ctVector = new Vector<>();

        try{
            ServerSocket ss = new ServerSocket(port);
            while(true){//adding indefinite clients
                Socket s = ss.accept();//returns Socket
                System.out.println("Connected: " + s.getInetAddress());
                ChatThread ct = new ChatThread(s, this);
                ctVector.add(ct);
                ct.start();
            }
        }catch(IOException ioe){
            System.out.println("ioe in ChatServer constructor: " + ioe.getMessage());
            ioe.printStackTrace();
        }
    }
    public static void main(String[] args){
        //The server application is only listening to the current computer
        new ChatServer(6789);
    }


    public void sendMessageToAllClients(String message, ChatThread sendingThread) {
        System.out.println(message);
        for(ChatThread ct : ctVector){
            if(sendingThread != ct){//comparing address will actually work, compared reference. We pass the same object reference
                ct.sendMessage(message);
            }
        }
    }
}
