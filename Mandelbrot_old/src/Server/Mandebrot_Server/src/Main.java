//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Main {
    private static double Zoom;
    private static double midPointX;
    private static double midPointY;


    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: <Zoomfactor> <MidPointX> <MidPointY>");
            System.exit(1);
        }
        
        //gets screen resolution
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth() / 16 * 7;
        int height = gd.getDisplayMode().getHeight() / 9 * 7;

        System.out.println(width + " " + height);

        //initializing
        Zoom = Double.parseDouble(args[0]);
        midPointX = Double.parseDouble(args[1]);
        midPointY = Double.parseDouble(args[2]);
        
        Presenter presenter = new Presenter();
        View view = new View(presenter);
        Model model = new Model(presenter);
        
        model.setZoomAndMidPoints(Zoom, midPointX, midPointY);
        model.setScreenResolution(width, height);
        
        presenter.setModelAndView(model, view);
        presenter.setWidthAndHeight(width, height);
        presenter.setZoom(Zoom);
        presenter.Start();
    }
}