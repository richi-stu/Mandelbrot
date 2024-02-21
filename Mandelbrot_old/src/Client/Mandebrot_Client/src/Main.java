import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Main {
    static int ID;
    //private Main main;
    public static ArrayList<Object> next_pict = new ArrayList<Object>();
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: <IP>");
            System.exit(1);
        }

        try {
            
            // RMI-Registry suchen
            Registry registry = LocateRegistry.getRegistry(args[0], 1099);

            // Remote-Objekt vom Server abrufen
            RMI remoteObjekt = (RMI) registry.lookup("rmi");

            //Erfassen und Übermittlung verfügbarenen Threads, sowie Empfang der Bildausschnitte
            int Threads = Runtime.getRuntime().availableProcessors();
            ArrayList<Object> list = remoteObjekt.getConnection(Threads);
            ID = (int) list.get(0);


            first_pict a = new first_pict(list);
            for(int i = 0; i < Threads; i++) {
                a.berechnen(i, ID, remoteObjekt);
            }



        }
        catch (Exception e) {
            System.err.println("Fehler beim Verbinden mit dem Server: " + e.toString());
        }
    }
}

