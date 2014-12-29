package ro.feedershop.inventar.services;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Se va genera un raport al facturilor procesate si al erorilor gasite.
 *
 * Created by nevastuica on 12/14/2014.
 */
public class RaportService {


        public static void scrieInRaport(String str){

            try {
                FileWriter raport = new FileWriter("RaportInventar.txt", true);
                raport.append(str);
                raport.append("\n");
                raport.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
