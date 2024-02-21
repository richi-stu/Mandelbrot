import java.awt.*;
import java.nio.ByteBuffer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class RMI_Implementation extends UnicastRemoteObject implements RMI{

    //Relation: ID[0][0] = "x","y";
    // x € N0; y € N*;
    // x = "ID"; y = "Threads"
    //[[x,y],[1,8],[2,4],...]
    private java.util.List<ArrayList<Integer>> ID = new ArrayList<ArrayList<Integer>>();

    //[[index, image],[1,image],[2,0],...]
    //index € N*
    //image € N0
    //if image = 0; no image for this index, => need to wait
    //image != 0; image object exists, => load it into the View
    private java.util.List<ArrayList<Object>> Images = new ArrayList<ArrayList<Object>>();
    private int Index = 0; //place in Images-List


    Model m;

    //Zoom has to be != 1.0
    private double Zoom;

    //data for client
    private double width; //screen resolution
    private double height; //screen resolution
    private double xmin;
    private double xmax;
    private double ymin;
    private double ymax;
    private double cr;
    private double ci;



    //appends the available Threads into the List
    public void appendID(int threads){
        ID.add(ID.size(),new ArrayList<Integer>(Arrays.asList(ID.size(), threads)));
    }

        //appends the Image into the placeholder, if exists. Instead, it appends at the end of the list
       synchronized public int appendImage(int index, int ID, Color[][] image){

           System.out.println("Images: " + Images);

            //checks if the placeholder exists and replace it with the image
            if(index < Images.size() && Images.get(index).contains(ID) == true && Images.get(index).get(1) == (Object) 0){
                Images.get(index).set(1,image);
                this.m.Images = this.Images;
                return 0;
            }
            //Debugging
            else{
                System.out.println("Index: " + index + "; Images.size(): " + Images.size() + "; Images.get(index).contains(ID): " + Images.get(index).contains(ID) + "; Images.get(index).get(1): " + Images.get(index).get(1) );
                System.out.println(Images.get(index) + " ID: " + ID + "; Index: " + index + "; index-1: " + Images.get(index-1) +"; index+1: " + Images.get(index+1));
                System.exit(1);
                return 1;
            }
        }



        //Appends a placeholder into List
        public void appendImage(int ID){
            Images.add(Images.size(),new ArrayList<Object>(Arrays.asList(ID, 0)));
        }




    //---------------------------------------RMI--------------------------------------

    //Constructor
    public RMI_Implementation(Model m) throws RemoteException {

        this.m = m;
        this.Zoom = m.Zoom;
        this.width = m.width;
        this.height = m.height;
        this.xmin = m.xmin;
        this.xmax = m.xmax;
        this.ymin = m.ymin;
        this.ymax = m.ymax;
        this.cr = m.cr;
        this.ci = m.ci;
    }


    //sends data / parameter
    @Override
    public ArrayList<Double> sendData(ArrayList<Object> DataPaket) throws RemoteException {
        // gets the ID and the place where the placeholder of the image is, to replace it with the image
        int ID = (int) DataPaket.get(0);
        int place = (int) DataPaket.get(1); //has each thread of client
        Color[][] image = (Color[][]) DataPaket.get(2);

        System.out.println("ID: " + ID + "; Place: " + place + "; Color[][]: " + image);


        //put it into the image list, replace the placeholder with the image
        if(appendImage(place, ID, image) == 0){

            System.out.println(Images);

            //sets new placeholder at the end of the image list
            appendImage(ID);
            System.out.println("Index: " + Index + "; Images.size(): " + Images.size() + "; Images.get(Index).contains(Index): " + Images.get((int)Index).contains(Index) + "; Images[Index][1]: " + Images.get((int)Index).get(1));
            this.m.Images = this.Images;
            this.m.interrupt();
        }
        else {
            System.out.println("Image couldn't insert, the placeholder doesn't exist");
            return null;
        }

        System.out.println(Images);

        //calculates the new data
        calcData();

        //put Data into the List
        ArrayList<Double> data = new ArrayList<Double>();
        data.add(width);
        data.add(height);
        data.add(xmin);
        data.add(xmax);
        data.add(ymin);
        data.add(ymax);
        data.add((double) Index);
        Index++;

        System.out.println(data);

        return data;
    }

    public void calcData(){
        double xdim = xmax - xmin;
        double ydim = ymax - ymin;
        xmin = cr - xdim / 2 / Zoom;
        xmax = cr + xdim / 2 / Zoom;
        ymin = ci - ydim / 2 / Zoom;
        ymax = ci + ydim / 2 / Zoom;

        System.out.println(xmin + "; " + xmax);
    }


    @Override
    public ArrayList<Object> getConnection(int Threads) throws RemoteException {

        //creates List for the necessary Info; [ID, [width, height, xmin, xmax, ymin, ymax]]
        ArrayList<Object> listObj = new ArrayList<Object>();
        System.out.println("New client connected! ID: " + ID.size());

        //add client
        listObj.add(ID.size());
        appendID(Threads);

        //sets a placeholder for the future incoming Color[][] in the list for each thread
        for(int i = 0; i < Threads; i++){

            Images.add(Images.size(),new ArrayList<Object>(Arrays.asList(ID.size()-1, 0)));
            this.m.Images = this.Images;

            listObj.add(Index); //is the place
            Index++;

            //calc the necessary data for each Thread / Image
            calcData();

            //put Data into the second List
            ArrayList<Object> data = new ArrayList<Object>();
            data.add(width);
            data.add(height);
            data.add(xmin);
            data.add(xmax);
            data.add(ymin);
            data.add(ymax);

            listObj.add(data);

            System.out.println(Images);
        }

        //sends the Data packet back
        return listObj;
    }

}