package CSCIServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by albertoprego on 10/20/15.
 */
public class ChatThread extends Thread{
    private Socket s;
    private BufferedReader br;
    private PrintWriter pw;
    private ChatServer cs;
    public ChatThread(Socket s, ChatServer cs) {
        this.s = s;
        this.cs = cs;
        try{
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream());
            //get messages from client and then forward it to the rest
        }catch (IOException ioe){
            System.out.println("ioe in ChatThread constructor: " + ioe.getMessage());
        }
    }

    @Override
    public void run(){
        try {
            while(true) {
                //if its windows it throws an exception, if its a MAC, it returns null
                String message = br.readLine();
                if(message == null){
                    break;
                }
                cs.sendMessageToAllClients(message, this);
            }
        } catch (IOException ioe) {
            System.out.println("ioe in ChatServer.run()" + ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if(pw != null){
            pw.println(message);
            pw.flush();
        }
    }
}
