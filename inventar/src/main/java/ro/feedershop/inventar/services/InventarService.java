package ro.feedershop.inventar.services;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ro.feedershop.inventar.beans.Factura;
import ro.feedershop.utile.Constante;
import ro.feedershop.utile.ExcelUtil;
import ro.feedershop.utile.FileUtil;

import java.io.IOException;
import java.util.*;

/**
 * Service care va genera fisieul de inventar pe baza Listei de facturi.
 * Pt fiecare factura din lista:
 * 1. se verifica daca exista codul de produs in lista de coduri de produse.
 * <p/>
 * Created by nevastuica on 12/13/2014.
 */
public class InventarService {

    final int ROW_NUME_CLIENT = 0;
    final int ROW_COD_FACT = 1;
    final int ROW_DATA_FACT = 2;
    final int COL_COD_PRODUS = 0;
    List<String> codProduse = new ArrayList<String>();
    Map<String, Integer> prodPosition = new HashMap<String, Integer>();
    int rowNum = 3; //row that hasn't a prodCode in it yet

    public void createExcel(String path, String filename, List<Factura> facturi) throws IOException {


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(Constante.SHEET_INVENTAR);
        //creez cele 3 randuri o singura data pt a nu supra scrie datele
        Row rowNume = sheet.createRow(ROW_NUME_CLIENT);
        Row rowCod = sheet.createRow(ROW_COD_FACT);
        Row rowData = sheet.createRow(ROW_DATA_FACT);

        int currentCol = 1;

        for (Factura factura : facturi) {
            if (factura != null) {
                writeFactHeader(factura, workbook, currentCol, rowNume, rowCod, rowData);
                writeProductContent(factura, workbook, currentCol);
            }

            currentCol++;
        }

        String nume = filename + Constante.EXTENSION;
        FileUtil.saveWorkbookOnDisk(path, nume, workbook);
    }

    public void writeProductContent(Factura factura, XSSFWorkbook workbook, int colNum) {
        Map produse = factura.getProduseCumparate();
        XSSFSheet sheet = workbook.getSheet(Constante.SHEET_INVENTAR);
        CellStyle style = ExcelUtil.getDefaultStyle(workbook);
        CellStyle boldStyle = ExcelUtil.getDefaultStyle(workbook);
        boldStyle.setFont(ExcelUtil.getDefaultFont(workbook, true, 8));

        if(produse != null) {
            Iterator it = produse.entrySet().iterator();
            RaportService.scrieInRaport("**************" + factura.getCodFactura() + "*****************");
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                String codProdus = pairs.getKey().toString();
                if (codProduse.contains(codProdus)) {
                    //adaug in rand deja existent
                    int rowNr = prodPosition.get(codProdus);
                    Row currentRow = sheet.getRow(rowNr);
                    Cell cantCell = currentRow.createCell(colNum);
                    ExcelUtil.setCellValueAndStyle(cantCell, pairs.getValue().toString(), style);
                } else {
                    Row currentRow = sheet.createRow(rowNum);
                    Cell prodCell = currentRow.createCell(COL_COD_PRODUS);
                    ExcelUtil.setCellValueAndStyle(prodCell, codProdus, boldStyle);
                    Cell cantCell = currentRow.createCell(colNum);
                    ExcelUtil.setCellValueAndStyle(cantCell, pairs.getValue().toString(), style);
                    codProduse.add(codProdus);
                    prodPosition.put(codProdus, new Integer(rowNum));
                    rowNum++;
                }
                RaportService.scrieInRaport(pairs.getKey() + " = " + pairs.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        else
        {
            RaportService.scrieInRaport("**************" + factura.getCodFactura() + "*********nulllll********");
        }

    }


    /**
     * Scriu capul de coloana cu informatiile despre factura
     *
     * @param factura  - factura curenta
     * @param workbook
     * @param colNum   - coloana curenta
     * @param rowNume  - randul cu numele clientului     *
     * @param rowCod   - randul cu codul facturii
     * @param rowData  - randul cu data facturii
     */
    public void writeFactHeader(Factura factura, XSSFWorkbook workbook, int colNum, Row rowNume,
                                Row rowCod, Row rowData) {

        CellStyle boldStyle = ExcelUtil.getDefaultStyle(workbook);
        boldStyle.setFont(ExcelUtil.getDefaultFont(workbook, true, 8));

        Cell celNume = rowNume.createCell(colNum);
        ExcelUtil.setCellValueAndStyle(celNume, factura.getNumeClient(), boldStyle);

        Cell celCod = rowCod.createCell(colNum);
        ExcelUtil.setCellValueAndStyle(celCod, factura.getCodFactura(), boldStyle);

        Cell celData = rowData.createCell(colNum);
        ExcelUtil.setCellValueAndStyle(celData, factura.getDataFactura(), boldStyle);
    }


}
