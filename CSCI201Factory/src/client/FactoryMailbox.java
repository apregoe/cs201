package client;

import libraries.ImageLibrary;
import resource.Resource;

import java.awt.*;
import java.util.*;

/**
 * Created by albertoprego on 10/20/15.
 */
public class FactoryMailbox extends FactoryObject {
    private Vector<Resource> available;
    protected Random rand = new Random();

    private Queue<Resource> mail;

    protected FactoryMailbox(Vector<Resource> deliveries) {
        super(new Rectangle(0,0,1,1));
        this.available = deliveries;
        mImage = ImageLibrary.getImage(Constants.resourceFolder + "mailbox" + Constants.png);
        mLabel = "Mailbox";
    }
    public Resource getStock() throws InterruptedException{
        /*
        int toStock = Math.abs(rand.nextInt() % available.size());
        int number = Math.abs(rand.nextInt() % 25 +1);
        return new Resource(available.elementAt(toStock).getName(), number);
        */

        while(mail.isEmpty()){
            Thread.sleep(200);
        }
        return mail.remove();
    }

    public void insert(Resource resource) {
        for (Resource r : available){
            if(r.getName().equals(resource.getName())){
                mail.add(resource);
                break;
            }
        }
    }
}
