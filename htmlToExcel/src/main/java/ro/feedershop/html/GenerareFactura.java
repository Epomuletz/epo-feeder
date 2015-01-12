package ro.feedershop.html;

import ro.feedershop.html.beans.Comanda;
import ro.feedershop.html.builders.InvoiceBuilder;
import ro.feedershop.html.util.ComandaUtil;

import javax.swing.*;
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
        String numeFactura = "Factura_FS00xxx_";
        String data = comanda.getDataComanda();
        String numeClient = comanda.getClient().getNumeComplet();
        numeFactura = numeFactura + data + "_" + numeClient.replace(" ", "");
        return numeFactura;
    }

    public static String creareFactura(String copied, String path) {
        Comanda comanda = ComandaUtil.populareComanda(copied);
        String numeExcel = getNumeExcel(comanda);

        try {

            InvoiceBuilder invoiceBuilder = new InvoiceBuilder();
            invoiceBuilder.createExcel(path, numeExcel, comanda);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numeExcel;
    }

    private static String colectarePathFolderFacturi(){
        JFrame frame = null;
        String path = (String) JOptionPane.showInputDialog(
                frame,
                "Va rugam sa introduceti calea completa catre directorul unde doriti stocarea facturii:\n"
                        + "C:\\DirectorFacturi",
                "Input Factura Path",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "D:\\C. facturi clienti\\2015");
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
            String path = colectarePathFolderFacturi();
//            String path = "D:\\fs-workspace\\epo-feeder\\testeFacturi\\";
            String numeFact = creareFactura(data, path);
            f.add(new Label("Factura cu numele <<"+numeFact+">> a fost generata cu succes! \r\n " +
                    "O poti verifica la "+ path, Label.CENTER));
            f.setSize(900, 300);
            f.setVisible(true);
        } catch (Exception ex) {
            f.add(new Label("Error:  " + ex.getStackTrace()));
            f.setSize(500, 500);
            f.setVisible(true);
        }
    }
}
