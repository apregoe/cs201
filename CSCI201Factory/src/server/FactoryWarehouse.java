package server;

import com.sun.corba.se.spi.activation.Server;
import resource.Factory;
import resource.Resource;

import java.util.Random;
import java.util.Vector;

/**
 * Created by albertoprego on 10/27/15.
 */
public class FactoryWarehouse implements Runnable {
    private Vector<ServerClientCommunicator> communicators;
    private volatile Vector<Resource> resources;
    Random rand = new Random();
    public FactoryWarehouse(Vector<ServerClientCommunicator> communicators){
        this.communicators = communicators;
    }

    public void setFactory(Factory factory){
        resources = factory.getResources();
    }

    @Override
    public void run() {
        while(resources == null){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true){
            try{
                Thread.sleep(7000);
                int toStock = Math.abs(rand.nextInt() % resources.size());
                int number = Math.abs(rand.nextInt() % 25 + 1);
                for(ServerClientCommunicator communicator : communicators){
                    communicator.sendResource(new Resource(resources.elementAt(toStock).getName(), number));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

















