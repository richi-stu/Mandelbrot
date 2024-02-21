import java.awt.*;
import java.nio.ByteBuffer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMI extends Remote {

    ArrayList<Double> sendData(ArrayList<Object> DataPaket) throws RemoteException; //Client -> Server

    ArrayList<Object> getConnection(int Thread) throws RemoteException;
}
