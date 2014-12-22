package ro.feedershop.inventar.services;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ro.feedershop.inventar.beans.Factura;
import ro.feedershop.utile.FileUtil;

import java.io.IOException;
import java.util.List;

/**
 * Service care va genera fisieul de inventar pe baza Listei de facturi.
 * Pt fiecare factura din lista:
 *  1. se verifica daca exista codul de produs in lista de coduri de produse.
 *
 * Created by nevastuica on 12/13/2014.
 */
public class InventarService {

    static final String EXTENSION = ".xlsx";
    public static final String SHEET_NAME = "Inventar";

    public void createExcel(String path, String filename, List<Factura> facturi) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(SHEET_NAME);


        String nume = filename + EXTENSION;
        FileUtil.saveWorkbookOnDisk(path, nume, workbook);
    }


}
