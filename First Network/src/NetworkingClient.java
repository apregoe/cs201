import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by albertoprego on 10/15/15.
 */
public class NetworkingClient {
    public NetworkingClient(String hostname, int port){
        try{
            Socket s = new Socket(hostname, port);
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pw = new PrintWriter(s.getOutputStream());

            pw.println("Hello Server");
            pw.flush();
            String line = br.readLine();
            System.out.println("Server said: " + line);

            br.close();
            pw.close();
            s.close();
        }catch(IOException ioe){

        }
    }
    public static void main(String[] args){
        new NetworkingClient("68.181.50.132", 6789);
    }
}
