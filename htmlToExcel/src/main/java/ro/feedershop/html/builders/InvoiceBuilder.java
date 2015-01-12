package ro.feedershop.html.builders;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ro.feedershop.html.beans.Adresa;
import ro.feedershop.html.beans.Client;
import ro.feedershop.html.beans.Comanda;
import ro.feedershop.html.beans.Produs;

import ro.feedershop.utile.ExcelUtil;
import ro.feedershop.utile.Constante;
import ro.feedershop.utile.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by nevastuica on 7/27/2014.
 */
public class InvoiceBuilder {

    public static final String SHEET_NAME = "Factura";
    public static final int LEFT_MARGIN = 0;
    public static final int RIGHT_MARGIN = 11;
    
    public void setTableHeader(XSSFWorkbook workbook, int top, int bot) {
        XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
        CellStyle boldStyle = ExcelUtil.getDefaultStyle(workbook);
        boldStyle.setFont(ExcelUtil.getDefaultFont(workbook, true, 8));

        final int BOT_ROW = bot;
        final int TOP_ROW = top;
        final int START = 0;

        ExcelUtil.drawHLine(sheet, TOP_ROW, START, RIGHT_MARGIN);
        ExcelUtil.drawHLine(sheet, BOT_ROW, START, RIGHT_MARGIN);
        ExcelUtil.drawVLine(sheet, TOP_ROW, BOT_ROW, START);
        ExcelUtil.drawVLine(sheet, TOP_ROW, BOT_ROW, START + 1);
        ExcelUtil.drawVLine(sheet, TOP_ROW, BOT_ROW, START + 3);
        ExcelUtil.drawVLine(sheet, TOP_ROW, BOT_ROW, START + 8);
        ExcelUtil.drawVLine(sheet, TOP_ROW, BOT_ROW, START + 9);
        ExcelUtil.drawVLine(sheet, TOP_ROW, BOT_ROW, START + 10);
        ExcelUtil.drawVLine(sheet, TOP_ROW, BOT_ROW, RIGHT_MARGIN);

//      //first header row
        Row row1 = sheet.createRow(TOP_ROW);
        Cell cell1 = row1.createCell(START + 8);
        ExcelUtil.setCellValueAndStyle(cell1, Constante.PRET, boldStyle);
        Cell cell2 = row1.createCell(START + 10);
        ExcelUtil.setCellValueAndStyle(cell2, Constante.VAL_TOTALA, boldStyle);

//        second header row
        Row row = sheet.createRow(TOP_ROW + 1);
        for (int i = 0; i < RIGHT_MARGIN; i++) {
            Cell cell = row.createCell(i);
            switch (i) {
                case START: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.NR_CRT, boldStyle);
                    break;
                }
                case (START + 3): {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.DENUMIRE, boldStyle);
                    break;
                }
                case (START + 1): {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.COD, boldStyle);
                    break;
                }
                case (START + 9): {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.CANTITATE, boldStyle);
                    break;
                }
                case (START + 10):
                case (START + 8): {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.TVA, boldStyle);
                    break;
                }
            }
        }
        //third header row
        Row row2 = sheet.createRow(TOP_ROW + 2);
        for (int i = 0; i <= RIGHT_MARGIN; i++) {
            Cell cell = row2.createCell(i);
            switch (i) {

                case (START + 8):
                case (START + 10): {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.UM, boldStyle);
                    break;
                }
            }
        }
    }

    public void addTitle(XSSFWorkbook workbook, String dataComanda) {

        final int COL_START = 5;
        XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
        Row row7 = sheet.getRow(8);
        Cell cell = row7.createCell(COL_START);

        CellStyle style = ExcelUtil.getDefaultStyle(workbook);
        style.setFont(ExcelUtil.getDefaultFont(workbook, true, 10));
        cell.setCellStyle(style);
        cell.setCellValue(Constante.TITLE);

        Row row8 = sheet.getRow(9);
        Cell cell1 = row8.createCell(COL_START);
        CellStyle style2 = ExcelUtil.getDefaultStyle(workbook);
        style2.setFont(ExcelUtil.getDefaultFont(workbook, false, 8));
        cell1.setCellStyle(style2);
        cell1.setCellValue(Constante.SERIE_NUMAR);

        Row row9 = sheet.getRow(10);
        Cell cell2 = row9.createCell(COL_START);
        cell2.setCellStyle(style2);
        cell2.setCellValue(Constante.DATA);

        Cell cell3 = row9.createCell(COL_START + 1);
        cell3.setCellStyle(style2);
        cell3.setCellValue(dataComanda);
    }

    public int addProducts(XSSFWorkbook workbook, List<Produs> produse, int startRow) {
        XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
        CellStyle style = ExcelUtil.getDefaultStyle(workbook);
        CellStyle centerStyle = ExcelUtil.getCenterStyle(workbook);
        style.setFont(ExcelUtil.getDefaultFont(workbook, true, 8));

        int col = 0;
        int nrCrt = 1;
        final int SEC_PAGE = 16;
        sheet.setColumnBreak(RIGHT_MARGIN );
        for (Produs produs : produse) {
            Row row = sheet.createRow(startRow);
            Row row2 = sheet.createRow(startRow + 1);

            Cell cell1 = row.createCell(col);
            ExcelUtil.setCellNumericValueAndStyle(cell1, nrCrt, style);

            Cell cell2 = row.createCell(col + 3);
            ExcelUtil.setCellValueAndStyle(cell2, produs.getNume(), style);

            Cell cell22 = row2.createCell(col + 3);
            ExcelUtil.setCellValueAndStyle(cell22, produs.getNumeLinia2(), style);

            Cell cell23 = row.createCell(col + 1);
            ExcelUtil.setCellValueAndStyle(cell23, produs.getCodIntern(), style);


            Cell cell3 = row.createCell(col + 8);
            ExcelUtil.setCellNumericValueAndStyle(cell3, Double.parseDouble(produs.getPret()), centerStyle);

            Cell cell4 = row.createCell(col + 9);
            ExcelUtil.setCellNumericValueAndStyle(cell4, Double.parseDouble(produs.getCantitate()), centerStyle);

            Cell cell5 = row.createCell(col + 10);
            ExcelUtil.setCellNumericValueAndStyle(cell5, Double.parseDouble(produs.getTotal()), centerStyle);

            ExcelUtil.drawVLine(sheet, startRow, startRow + 2, col);
            ExcelUtil.drawVLine(sheet, startRow, startRow + 2, col + 1);
            ExcelUtil.drawVLine(sheet, startRow, startRow + 2, col + 3);
            ExcelUtil.drawVLine(sheet, startRow, startRow + 2, col + 8);
            ExcelUtil.drawVLine(sheet, startRow, startRow + 2, col + 9);
            ExcelUtil.drawVLine(sheet, startRow, startRow + 2, col + 10);
            ExcelUtil.drawVLine(sheet, startRow, startRow + 2, RIGHT_MARGIN);
            nrCrt++;
            startRow += 2;
            ExcelUtil.drawHLine(sheet, startRow, LEFT_MARGIN, RIGHT_MARGIN);

            if (nrCrt == SEC_PAGE) {
                sheet.setRowBreak(startRow++);
//                Row pageRow = sheet.createRow(startRow);
//                Cell pageCell = pageRow.createCell(4);
//                ExcelUtil.setCellValueAndStyle(pageCell, "Pag 1 / 2", style);

                Footer footer = sheet.getFirstFooter();
                footer.setCenter("Pag 1 / 2");
//                startRow += 3;
//                ExcelUtil.drawHLine(sheet, startRow, LEFT_MARGIN, RIGHT_MARGIN);
                startRow++;
                setTableHeader(workbook, startRow, startRow + 3);
                startRow += 3;
            }
        }
        if (nrCrt < SEC_PAGE) {
            Footer footer = sheet.getFirstFooter();
            footer.setCenter("Pag 1 / 1");
        }
        return startRow;
    }


    public void setFooter(XSSFWorkbook workbook, int startRow, Comanda comanda) {
        XSSFSheet sheet = workbook.getSheet(SHEET_NAME);

        CellStyle style = ExcelUtil.getDefaultStyle(workbook);
        style.setFont(ExcelUtil.getDefaultFont(workbook, false, 8));
        Row row = sheet.createRow(startRow + 1);
        Cell cell = row.createCell(0);
        ExcelUtil.setCellValueAndStyle(cell, Constante.INTOCMIT, style);
        ExcelUtil.drawVLine(sheet, startRow, startRow + 8, LEFT_MARGIN);
        ExcelUtil.drawVLine(sheet, startRow, startRow + 8, RIGHT_MARGIN);
        ExcelUtil.drawHLine(sheet, startRow + 2, LEFT_MARGIN, RIGHT_MARGIN);

        Row row2 = sheet.createRow(startRow + 2);
        Cell cell2 = row2.createCell(0);
        CellStyle style2 = ExcelUtil.getDefaultStyle(workbook);
        style2.setFont(ExcelUtil.getDefaultFont(workbook, false, 9));
        ExcelUtil.setCellValueAndStyle(cell2, Constante.SEMNATURA, style2);

        final int COL2 = 5;
        CellStyle boldStyle = ExcelUtil.getDefaultStyle(workbook);
        boldStyle.setFont(ExcelUtil.getDefaultFont(workbook, true, 9));
        ExcelUtil.drawVLine(sheet, startRow + 2, startRow + 8, COL2);
        ExcelUtil.drawHLine(sheet, startRow + 3, COL2, RIGHT_MARGIN);
        ExcelUtil.drawHLine(sheet, startRow + 4, COL2, RIGHT_MARGIN);
        ExcelUtil.drawHLine(sheet, startRow + 5, COL2, RIGHT_MARGIN);
        Cell cell3 = row2.createCell(COL2);
        ExcelUtil.setCellValueAndStyle(cell3, Constante.SUBTOTAL, style2);

        Cell cell31 = row2.createCell(COL2 + 4);
        ExcelUtil.setCellValueAndStyle(cell31, comanda.getPretTotal().toString(), boldStyle);

        Row row3 = sheet.createRow(startRow + 3);
        Cell cell4 = row3.createCell(COL2);
        ExcelUtil.setCellValueAndStyle(cell4, Constante.TAXA_CURIER, style2);
        Cell cell41 = row3.createCell(COL2 + 4);
        ExcelUtil.setCellValueAndStyle(cell41, comanda.getLivrareManipulare().toString(), boldStyle);
        row3.setHeight((short) 700);

        Row row4 = sheet.createRow(startRow + 4);
        Cell cell5 = row4.createCell(COL2);
        ExcelUtil.setCellValueAndStyle(cell5, Constante.TOTAL_FINAL, boldStyle);
        Cell cell51 = row4.createCell(COL2 + 4);
        Double pretTotal = comanda.getPretTotal() + comanda.getLivrareManipulare();
        ExcelUtil.setCellValueAndStyle(cell51, pretTotal.toString(), boldStyle);

        Row row5 = sheet.createRow(startRow + 5);
        Cell cell6 = row5.createCell(COL2);
        ExcelUtil.setCellValueAndStyle(cell6, Constante.SEMNATURA_PRIMIRE, style2);
        Row row6 = sheet.createRow(startRow + 6);
        Cell cell7 = row6.createCell(COL2);
        ExcelUtil.setCellValueAndStyle(cell7, Constante.POSTA, boldStyle);
        ExcelUtil.drawVLine(sheet, startRow + 2, startRow + 8, COL2 + 4);
        ExcelUtil.drawHLine(sheet, startRow + 8, LEFT_MARGIN, RIGHT_MARGIN);

//        linkuri
        ExcelUtil.drawHLine(sheet, startRow + 10, LEFT_MARGIN, RIGHT_MARGIN);
        ExcelUtil.drawVLine(sheet, startRow + 8, startRow + 10, LEFT_MARGIN);
        ExcelUtil.drawVLine(sheet, startRow + 8, startRow + 10, RIGHT_MARGIN);
        Row rowL1 = sheet.createRow(startRow + 8);
        Row rowL2 = sheet.createRow(startRow + 9);
        Cell cell1 = rowL1.createCell(COL2);
        Cell cellL2 = rowL2.createCell(COL2 - 1);
        ExcelUtil.setCellValueAndStyle(cell1, Constante.LINK1, boldStyle);
        ExcelUtil.setCellValueAndStyle(cellL2, Constante.LINK2, boldStyle);

    }

    public void createExcel(String path, String filename, Comanda comanda) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(SHEET_NAME);

        ExcelUtil.setExcelFormating(workbook);
        addLogo(workbook, sheet, 5, 1);

        setFurnizor(workbook);
        setClient(workbook, comanda.getClient(), 0, 6);
        addTitle(workbook, comanda.getDataComanda());
        setTableHeader(workbook, 13, 16);
        int lastRow = addProducts(workbook, comanda.getProduseComandate(), 16);
        setFooter(workbook, lastRow, comanda);


        String nume = filename + Constante.EXTENSION;
        FileUtil.saveWorkbookOnDisk(path, nume, workbook);
    }

    public static void setFurnizor(XSSFWorkbook workbook) {
        final int COL = 0;

        CellStyle boldStyle = ExcelUtil.getDefaultStyle(workbook);
        boldStyle.setFont(ExcelUtil.getDefaultFont(workbook, true, 8));

        CellStyle style = ExcelUtil.getDefaultStyle(workbook);
        style.setFont(ExcelUtil.getDefaultFont(workbook, false, 8));
        XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
        final int START = 0;
        final int END = 10;

        for (int i = START; i <= END; i++) {
            Cell cell = ExcelUtil.createCell(sheet, i, COL);
            switch (i) {
                case START: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.FURNIZOR, boldStyle);
                    break;
                }
                case START + 1: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.NUME_FIRMA, style);
                    break;
                }
                case START + 2: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.NR_REG, style);
                    break;
                }
                case START + 3: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.CUI, style);
                    break;
                }
                case START + 4: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.LOCALITATE, style);
                    break;
                }
                case START + 5: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.ADRESA_FIRMA1, style);
                    break;
                }
                case START + 6: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.ADRESA_FIRMA2, style);
                    break;
                }
                case START + 7: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.ADRESA_FIRMA3, style);
                    break;
                }
                case START + 8: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.BANCA, style);
                    break;
                }
                case START + 9: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.CONT, style);
                    break;
                }
                case START + 10: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.CAPITAL_SOCIAL, style);
                    break;
                }
            }
        }
    }

    public static void setClient(XSSFWorkbook workbook, Client client, int rowStart, int rowEnd) {
        final int COL = 8;

        CellStyle boldStyle = ExcelUtil.getDefaultStyle(workbook);
        boldStyle.setFont(ExcelUtil.getDefaultFont(workbook, true, 8));

        CellStyle style = ExcelUtil.getDefaultStyle(workbook);
        style.setFont(ExcelUtil.getDefaultFont(workbook, false, 8));
        XSSFSheet sheet = workbook.getSheet(SHEET_NAME);

        Adresa adrFacturare = client.getAdresaFacturare();
        for (int i = rowStart; i <= rowEnd; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.createCell(COL);
            switch (i) {
                case 0: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.CLIENT, boldStyle);
                    break;
                }
                case 1: {
                    ExcelUtil.setCellValueAndStyle(cell, client.getNumeComplet(), style);
                    break;
                }
                case 2: {
                    ExcelUtil.setCellValueAndStyle(cell, Constante.ADRESA, boldStyle);
                    break;
                }
                case 3: {
                    ExcelUtil.setCellValueAndStyle(cell, adrFacturare.getLinia1(), style);
                    break;
                }
                case 4: {
                    ExcelUtil.setCellValueAndStyle(cell, adrFacturare.getLinia2(), style);
                    break;
                }
                case 5: {
                    ExcelUtil.setCellValueAndStyle(cell, adrFacturare.getLinia3(), style);
                    break;
                }
                case 6: {
                    ExcelUtil.setCellValueAndStyle(cell, adrFacturare.getTelefon(), style);
                    break;
                }
            }
        }

    }

    public void addLogo(XSSFWorkbook workbook, XSSFSheet sheet, int row, int col) throws IOException {
        //        add logo
        InputStream is = getClass().getResourceAsStream("/FeederLogo.jpg");
        byte[] bytes = IOUtils.toByteArray(is);
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        is.close();
        CreationHelper helper = workbook.getCreationHelper();
        // Create the drawing patriarch.  This is the top level container for all shapes.
        Drawing drawing = sheet.createDrawingPatriarch();

        //add a picture shape
        ClientAnchor anchor = helper.createClientAnchor();
        //set top-left corner of the picture,
        //subsequent call of Picture#resize() will operate relative to it
        anchor.setCol1(row);
        anchor.setRow1(col);
        Picture pict = drawing.createPicture(anchor, pictureIdx);

        //auto-size picture relative to its top-left corner
        pict.resize(0.7);
    }

}
