import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.nio.ByteOrder;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Test_Client{

    private static int place;

    public static void main(String[] args){

        try {
            // RMI-Registry suchen
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);




            // Remote-Objekt vom Server abrufen
            RMI remoteObjekt = (RMI) registry.lookup("rmi");


            int Threads = 12;
            ArrayList<Object> list = remoteObjekt.getConnection(Threads);
            int ID = (int) list.get(0);

            for(int i = 0; i < Threads; i++) {

                ArrayList<Object> list2 = new ArrayList<Object>();

                if(i == 0){
                    place = (int) list.get(1);
                }
                else{
                    place = (int) list.get(i+2);
                }


                list2 = (ArrayList<Object>) list.get(2);

                double width = (Double) list2.get(0);
                double height = (Double) list2.get(1);
                double xmin = (Double) list2.get(2);
                double xmax = (Double) list2.get(3);
                double ymin = (Double) list2.get(4);
                double ymax = (Double) list2.get(5);


                System.out.println("Color[][]: " + list);
                System.out.println("ID: " + ID + "\nplace: " + place + "\nWidth: " + width + "\nHeight: " + height + "\nxmin: " + xmin + "\nxmax: " + xmax + "\nymin: " + ymin + "\nymax: " + ymax);

                Color[][] c = new Color[(int) width][(int) height]; //höhe und breite nur so aufm Laptop

                for (int y = 0; y < height; ++y) {
                    for (int x = 0; x < width; ++x) {
                        if (c[x][y] == null) {
                            if(place%2 == 0 || place%4 == 0){
                                c[x][y] = Color.BLUE;
                            }
                            else{
                                c[x][y] = Color.RED;
                            }
                        }
                    }
                }

                ArrayList<Object> data = new ArrayList<Object>();
                data.add(ID);
                data.add(place);
                data.add(c);

                ArrayList<Double> dataReturn = remoteObjekt.sendData(data);
                System.out.println("HIER SIND DIE DATEN: " + dataReturn);

            }




            //System.out.println("Threads: " + receivedData.getInt());

            //System.out.println("Empfangene Daten: " + receivedData);
        } catch (Exception e) {
            System.err.println("Fehler beim Verbinden mit dem Server: " + e.toString());
        }

    }


}