import java.awt.*;
import java.nio.ByteBuffer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

public interface RMI extends Remote {

    ByteBuffer sendData(ByteBuffer DataPaket) throws RemoteException; //Server -> Client

    void workOnRequest(ByteBuffer DataPaket) throws RemoteException; //Client -> Server

    ByteBuffer getConnection(ByteBuffer DataPaket) throws RemoteException; //Client -> Server, necessary for the first connection request

    String sayHello(byte[] b) throws RemoteException;

    ArrayList<Object> getColor(Color[][] c) throws RemoteException;
}
