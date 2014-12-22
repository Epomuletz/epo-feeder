package ro.feedershop.inventar;

import org.apache.commons.io.FileUtils;
import ro.feedershop.inventar.beans.Factura;
import ro.feedershop.inventar.services.FacturaService;
import ro.feedershop.inventar.services.InventarService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nevastuica on 12/13/2014.
 */
public class GenerareInventar {


    /**
     * @param path
     * @return
     */
    Collection<File> colectareFisiereFacturi(String path) {
        //TODO: posibilitatea selectarii folderului de intrare: swing input

        File directory = new File(path);
        String[] extensions = {"xlsx"};
        return FileUtils.listFiles(directory, extensions, true);
//        for (File factura : facturi) {
//            System.out.println("Nume: " + factura.getName());
//        }

    }

    /**
     * Flow-ul central al modulului de inventar.
     */
    public void procesareFacturi() {
        String path = "D:\\fs-workspace\\epo-feeder\\ptInventar\\2014";
        String numeInv = "Inventar2014";
        String exceptieNume = "Feeder";
        String exceptieNume2 = "Centralizator";

        Collection<File> fisFacturi = colectareFisiereFacturi(path);
        FacturaService facturaService = new FacturaService();
        List<Factura> facturi = new ArrayList<Factura>();
        for (File fis : fisFacturi) {
            String numeFact = fis.getName();
            if (!numeFact.contains(exceptieNume) && !numeFact.contains(exceptieNume2)) {
                Factura fact = facturaService.readFactInfoFromExcel(fis);
                facturi.add(fact);
            }
        }
        //scriu in fis de inventar
        InventarService inventarService = new InventarService();
        try {
            inventarService.createExcel(path, numeInv, facturi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(final String[] args) {

        GenerareInventar inventar = new GenerareInventar();
        inventar.procesareFacturi();

    }
}
