import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Test_Server {

    public static void main(String[] args){
        try {
            RMI remoteObject = new RMI_Implementation();

            //export remote object and listen at the port
            if (UnicastRemoteObject.unexportObject(remoteObject, false)) {
                remoteObject = (RMI) UnicastRemoteObject.exportObject(remoteObject, 1099); //don't need sudo rights
            }

            //create RMI-Registry and bind remote object
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("rmi", remoteObject);

            System.out.println("Server started!");
        }
        catch(Exception e){
            System.err.println("Couldn't start server: " + e.toString());
        }
    }



}