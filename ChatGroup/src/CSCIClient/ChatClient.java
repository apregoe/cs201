package CSCIClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by albertoprego on 10/20/15.
 */
public class ChatClient extends Thread {

    private PrintWriter pw;
    private BufferedReader br;

    //In networking the client needs to know the IP address and port
    public ChatClient(String hostname, int port){
        try{
            Socket s = new Socket(hostname, port);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream());
            this.start();
            Scanner scan = new Scanner(System.in);
            while(true){
                String line = scan.nextLine();
                pw.println("Prezcott: "+ line);
                pw.flush();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        new ChatClient("192.168.1.123", 6789);
    }

    @Override
    public void run(){
        try {
            while (true) {
                String message = br.readLine();
                if(message != null){
                    break;
                }
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
