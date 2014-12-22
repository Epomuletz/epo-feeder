package ro.feedershop.html;

import ro.feedershop.html.beans.Comanda;
import ro.feedershop.html.builders.InvoiceBuilder;
import ro.feedershop.html.util.ComandaUtil;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * @author <a href="mailto:elena.banu@endava.com">Elena Banu</a>
 * @Revision $Rev$
 * @since 0.0.1
 */
public class GenerareFactura {


    public static String getCopiedData() throws IOException {
        String data = "no data in clipboard";
        try {
            data = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static String getNumeExcel(Comanda comanda) {
        String numeFactura = "Factura_FS000xx_";
        String data = comanda.getDataComanda();
        String numeClient = comanda.getClient().getNumeComplet();
        numeFactura = numeFactura + data + "_" + numeClient.replace(" ", "");
        return numeFactura;
    }

    public static String creareFactura(String copied) {
        Comanda comanda = ComandaUtil.populareComanda(copied);

//        String path = "D:\\fs-workspace\\epo-feeder\\testeFacturi\\";
//        path claude
        String path = "D:\\C. facturi clienti\\2014";
        try {
            String numeExcel = getNumeExcel(comanda);
            InvoiceBuilder invoiceBuilder = new InvoiceBuilder();
            invoiceBuilder.createExcel(path, numeExcel, comanda);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    public static void main(String[] args) {

        String data = null;
        try {
            data = getCopiedData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Frame f = new Frame();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setTitle("FeederShop - Generare Facturi - Buna Schmupex!");
        try {
            String path = creareFactura(data);
            f.add(new Label("Factura a fost generata cu succes! \n O poti verifica la " + path));
            f.setSize(800, 300);
            f.setVisible(true);
        } catch (Exception ex) {
            f.add(new Label("Error:  " + ex.getMessage()));
            f.setSize(500, 500);
            f.setVisible(true);
        }
    }
}
