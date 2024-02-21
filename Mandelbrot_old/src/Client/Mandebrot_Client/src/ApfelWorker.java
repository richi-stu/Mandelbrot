import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ApfelWorker extends Thread{
    private final RMI remoteObjekt;
    int y_sta, y_sto, ypix, xpix, place, ID;
    double xmin, xmax, ymin, ymax;
    final int max_iter = 5000;

    public ApfelWorker (int y_start, int y_stopp, double xmin, double xmax, double ymin, double ymax, int ypix, int xpix, int place, int ID, RMI remoteObjekt) {
        this.y_sta = y_start;
        this.y_sto = y_stopp;
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        this.ypix = ypix;
        this.xpix = xpix;
        this.place = place;
        this.ID = ID;
        this.remoteObjekt = remoteObjekt;

    }

    public void run() {
        double c_re, c_im;
        int[][] bildIter = new int[xpix][ypix];
        Color[][] bild;
        bild = new Color[xpix][ypix];

        // Berechnung des Mandelbrot für den zugeteilten Bereich
        for (int y = y_sta; y < y_sto; y++) {
            c_im = ymin + (ymax - ymin) * y / ypix;

            for (int x = 0; x < xpix; x++) {
                c_re = xmin + (xmax - xmin) * x / xpix;
                int iter = calc(c_re, c_im);
                bildIter[x][y] = iter;
                Color pix = farbwert(iter); // Farbberechnung
                // if (iter == max_iter) pix = Color.RED; else pix = Color.WHITE;
                // v.image.setRGB(x, y, pix.getRGB()); // rgb

                bild[x][y] = pix;

            }
        }
        System.out.println(bild);

        // Senden der berechneten Daten an den Server
        ArrayList<Object> data = new ArrayList<Object>();
        data.add(ID);
        data.add(place);
        data.add(bild);

        // Senden des Bildausschnittes und empfangen der Daten für den neuen Bildausschnitt
        ArrayList<Double> dataReturn = null;
        try {
            dataReturn = remoteObjekt.sendData(data);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        //Hilfe beim debbuging
        if (dataReturn == null){
            System.out.println("HIER IST DER BUG: " + data);
            System.exit(1);
        }
        System.out.println("HIER SIND DIE DATEN: " + dataReturn);

        //Auslesen der Daten für den neuen Bildausschnitt
        double nxmin = (Double) dataReturn.get(2);
        double nxmax = (Double) dataReturn.get(3);
        double nymin = (Double) dataReturn.get(4);
        double nymax = (Double) dataReturn.get(5);
        int nypix = (int) Math.round((double)dataReturn.get(1));
        int nxpix = (int) Math.round((double)dataReturn.get(0));
        int nplace = (int) Math.round((double)dataReturn.get(6));

        // Erzeugen eines neuen ApfelWorker-Threads und Starten der Berechnung für den nächsten Bereich
        ApfelWorker worker = new ApfelWorker(0, ypix,  nxmin,  nxmax,  nymin,  nymax,  nypix,  nxpix, nplace, ID, remoteObjekt);
        worker.start();
    }

    /**
     * @param cr Realteil
     * @param ci Imaginärteil
     * @return Iterationen
     */
    public int calc(double cr, double ci) {
        int iter;

        final double max_betrag2 = 4;
        double zr, zi, zr2 = 0, zi2 = 0, zri = 0, betrag2 = 0;
        //  z_{n+1} = z²_n + c
        //  z²  = x² - y² + i(2xy)
        // |z|² = x² + y²
        for (iter = 0; iter < max_iter && betrag2 <= max_betrag2; iter++) {
            zr = zr2 - zi2 + cr;
            zi = zri + zri + ci;

            zr2 = zr * zr;
            zi2 = zi * zi;
            zri = zr * zi;
            betrag2 = zr2 + zi2;
        }
        return iter;
    }

    /**
     * @param iter Iterationszahl
     * @return Farbwert nsmooth = n + 1 - Math.log(Math.log(zn.abs()))/Math.log(2)
     *     Color.HSBtoRGB(0.95f + 10 * smoothcolor ,0.6f,1.0f);
     */
    Color farbwert(int iter) {
        if (true) {
            if (iter == max_iter) return Color.BLACK;
            if (iter%3 == 0) return Color.BLUE;
            if (iter%5 == 0) return Color.CYAN;
            if (iter%7 == 0) return Color.DARK_GRAY;
            if (iter%11 == 0) return Color.GREEN;
            else return Color.RED;
        }
        return Color.BLACK;
    }

}
