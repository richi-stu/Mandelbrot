import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Presenter {
    private Model model;
    private View view;
    private int width;
    private int height;

    Presenter() {

    }

    public void setModelAndView(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void setWidthAndHeight(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setZoom(double zoom) {

        if(zoom == 1.0){
            System.out.println("Zoom has to be larger or lower then 1.0!");
            System.exit(1);
        }
    }

    private void waitForClients(){

        try {
            RMI remoteObject = new RMI_Implementation(this.model);

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

    public void setImage(Color[][] image){
        this.view.update(image);
    }


    public void Start() {
        this.view.UI(this.width, this.height);

        this.waitForClients();
        this.model.start();
    }
}