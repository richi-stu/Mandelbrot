import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

//own List implementation
public class List {

    //creates normal List Object
    private java.util.List<ArrayList<Integer>> ID = new ArrayList<ArrayList<Integer>>();
    private java.util.List<ArrayList<Object>> Images = new ArrayList<ArrayList<Object>>();


    //corrects the list if an item got removed, => changes the ID of each Client
    private void correctList(){
        for(int i = 0; i < ID.size(); i++){
            if(!ID.get(i).contains(i)){
                ID.get(i).set(0,i);
            }
        }
    }

    //appends the available Threads into the List
    public void appendID(int threads){
        ID.add(ID.size(),new ArrayList<Integer>(Arrays.asList(ID.size(), threads)));
    }

    public void appendImage(int index, Color[][] image){

        System.out.println("Before Checker: " + Images);

        if(index < Images.size() && Images.get(index).contains(index) && Images.get(index).get(1) == (Object) 0){
            System.out.println("CHECKER: " + Images);
            Images.get(index).set(1,image);
            System.out.println("After Checker: " + Images);
            return;
        }


        System.out.println("SIZE: " + Images.size());

        Images.add(index,new ArrayList<Object>(Arrays.asList(Images.size(), image)));

        System.out.println("sjdf");
        System.out.println("Images[0][0]: " + Images.get(0).get(0));
        System.out.println("Normal path: " + Images);




    }

    public void appendImage(int Index){
        Images.add(Index,new ArrayList<Object>(Arrays.asList(Images.size(), 0)));
    }


    //removes the entry from list
    //Client got disconnected
    //Each client gets another ID
    public void remove(int index){
        ID.remove(index);
        this.correctList();
    }

    //shows all items in List
    public java.util.List showID(){
        return ID;
    }

    public java.util.List showImages(){
        return Images;
    }

}
