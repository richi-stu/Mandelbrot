import java.awt.*;
import java.nio.ByteBuffer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMI_Implementation extends UnicastRemoteObject implements RMI {


    //Relation: ID[0][0] = "x","y";
    // x € N0; y € N*;
    // x = "ID"; y = "Threads"
    //[[x,y],[1,8],[2,4],...]
    private List ID = new List();

    //[[index, image],[1,image],[2,0],...]
    //index € N*
    //image € N0
    //if image = 0; no image for this index, => need to wait
    //image != 0; image object exists, => load it into the View
    private List Images = new List();
    private int Index = 0;

    //Constructor
    public RMI_Implementation() throws RemoteException {
       /*
        ID.append(1);
        ID.append(2);
        ID.append(3);
        System.out.println(ID.show());
        ID.remove(1);
        System.out.println(ID.show());
        ID.append(4);
        ID.append(5);

        System.out.println(ID.show());
        ID.remove(1);
        ID.append(6);
        System.out.println(ID.show());


        System.out.println("slkdjohfs");
        for (int i = 0; i < 10; i++) {
            //Model.calcData();
            //Data.put(Model.fillData());
            Images.append(Index, 0);
            Index++;
        }


        System.out.println(Images.showImages());
        */

        ByteBuffer b = null;
        sendData(b);

    }

    public String sayHello(byte[] b){

        ByteBuffer ba = ByteBuffer.wrap(b);

        System.out.println("Content: " + ba.getInt());


        System.out.println("ich wurde aufgerufen 'Server'");
        return "Hello";
    }

    //sends data
    @Override
    public ByteBuffer sendData(ByteBuffer DataPaket) throws RemoteException {
        // gets the amount of Threads from the Client
       // int Threads = DataPaket.getInt();
       // int index = DataPaket.getInt(); // index of the image

        //getting the Images
        //Extract the Color[][] out of the ByteBuffer
        //TODO Richard fragen

        Color[][] c = new Color[1][1];

         Images.appendImage(0);
        Images.appendImage(0,c);
        Images.appendImage(1,c);



        return DataPaket; // falsch

    }

    //receives data and working on
    @Override
    public void workOnRequest(ByteBuffer DataPaket) throws RemoteException {

    }

    @Override
    public ByteBuffer getConnection(ByteBuffer DataPaket) throws RemoteException {

        // gets the amount of Threads from the Client
        int Threads = DataPaket.getInt();
        ID.appendID(Threads);


        //create Data buffer
        ByteBuffer Data;
        Data = ByteBuffer.allocate(Threads);

        //fill the buffer with the data
        for (int i = 0; i < Threads; i++) {
            Index++;
          //  Model.calcData();
            //Data.put(Model.fillData());
            Images.appendImage(Index);
        }

        Color[][] c = new Color[1][1];
        //Data.put(c);

        //sends the Data packet back
        return DataPaket;
    }

    @Override
    public ArrayList<Object> getColor(Color[][] c) {
        ArrayList<Object> a = new ArrayList<Object>();
        a.add(1);
        a.add(c);

        return a;
    }
}