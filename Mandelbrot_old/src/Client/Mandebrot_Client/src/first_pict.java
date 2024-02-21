import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class first_pict{
    ArrayList<Object> list;
    first_pict (ArrayList<Object> list){
        this.list = list;
    }
    void berechnen(int i, int ID, RMI remoteObjekt) throws RemoteException {
        int place;
        ArrayList<Object> list2 = new ArrayList<Object>();

        //Auslesen der Stelle des Bildes
        if(i == 0){
            place = (int) list.get(1);
        }
        else{
            System.out.println(list  + "\n"+ i);
            place = (int) list.get(2*i+1);
        }

        System.out.println(list);

        // Auslesen der Informationen zur Berechnung des Bildauschschnittes
        list2 = (ArrayList<Object>) list.get(2*i+2);
        double width = (Double) list2.get(0);
        double height = (Double) list2.get(1);
        double xmin = (Double) list2.get(2);
        double xmax = (Double) list2.get(3);
        double ymin = (Double) list2.get(4);
        double ymax = (Double) list2.get(5);
        int ypix = (int) Math.round((double)list2.get(1));
        int xpix = (int) Math.round((double)list2.get(0));

        //Konsolenausgaben für debug
        System.out.println("Vergleichsdaten:" + list2);
        System.out.println("Color[][]: " + list2);
        System.out.println("ID: " + ID + "\nplace: " + place + "\nWidth: " + width + "\nHeight: " + height + "\nxmin: " + xmin + "\nxmax: " + xmax + "\nymin: " + ymin + "\nymax: " + ymax);

        // Erzeugen eines ApfelWorker-Threads und Starten der Berechnung
        ApfelWorker worker = new ApfelWorker(0, ypix,  xmin,  xmax,  ymin,  ymax,  ypix,  xpix, place, ID, remoteObjekt);
        worker.start();

    }
}

