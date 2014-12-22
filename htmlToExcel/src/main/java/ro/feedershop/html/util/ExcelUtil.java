package ro.feedershop.html.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import ro.feedershop.html.beans.Adresa;
import ro.feedershop.html.beans.Client;
import ro.feedershop.html.beans.Comanda;
import ro.feedershop.html.beans.Produs;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="mailto:elena.banu@endava.com">Elena Banu</a>
 * @Revision $Rev$
 * @since 0.0.1
 */
public class ExcelUtil {


    public static final String SHEET_NAME = "Factura";
    public static final int LEFT_MARGIN = 0;
    public static final int RIGHT_MARGIN = 11;

    public static CellStyle getDefaultStyle(XSSFWorkbook workbook) {

        CellStyle style = workbook.createCellStyle();
        return style;
    }

    public static CellStyle getCenterStyle(XSSFWorkbook workbook) {

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        return style;
    }


    /**
     * Arial 10 black bold
     *
     * @param workbook
     * @return
     */
    public static XSSFFont getDefaultFont(XSSFWorkbook workbook, boolean isBold, int fontSize) {
        XSSFFont defaultFont = workbook.createFont();
        defaultFont.setFontHeightInPoints((short) fontSize);
        defaultFont.setFontName("Arial");
        defaultFont.setColor(IndexedColors.BLACK.getIndex());
        defaultFont.setBold(isBold);
        defaultFont.setItalic(false);
        return defaultFont;
    }

    public static void setExcelFormating(XSSFWorkbook workbook) {

        XSSFSheet sheet = workbook.getSheet(SHEET_NAME);

        //top rectangle
        drawRectangle(sheet, 0, 12, LEFT_MARGIN, RIGHT_MARGIN);
        //  small rectangle
        drawRectangle(sheet, 9, 11, 5, 7);
//        //products rectangle
//        drawRectangle(sheet, 12, 60, 0, 10);

        //formatting
        sheet.setColumnWidth(0, 1200);
        sheet.setColumnWidth(3, 1200);
        sheet.setColumnWidth(4, 1200);
        sheet.setColumnWidth(9, 1800);
        Row r = sheet.createRow(10);
        r.setHeight((short) 200);

        Row r1 = sheet.createRow(11);
        r1.setHeight((short) 200);

//        printing
        XSSFPrintSetup ps = (XSSFPrintSetup) sheet.getPrintSetup();
        sheet.setAutobreaks(true);
        sheet.setFitToPage(true);

    }

    public static void drawHLine(XSSFSheet sheet, int row1, int col1, int col2) {
        final double LINE_WIDTH = 0.7;

        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        XSSFClientAnchor reg2 = patriarch.createAnchor(0, 0, 0, 0, (short) col1, row1, (short) col2, row1);
        XSSFSimpleShape reg2Shape = patriarch.createSimpleShape(reg2);
        reg2Shape.setShapeType(ShapeTypes.LINE);
        reg2Shape.setLineStyleColor(0, 0, 0);
        reg2Shape.setLineWidth(LINE_WIDTH);
    }

    public static void drawVLine(XSSFSheet sheet, int row1, int row2, int col) {
        final double LINE_WIDTH = 0.7;

        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        XSSFClientAnchor reg2 = patriarch.createAnchor(1, 1, 1, 1, (short) col, row1, (short) col, row2);
        XSSFSimpleShape reg2Shape = patriarch.createSimpleShape(reg2);
        reg2Shape.setShapeType(ShapeTypes.LINE);
        reg2Shape.setLineStyleColor(0, 0, 0);
        reg2Shape.setLineWidth(LINE_WIDTH);
    }


    public static void drawRectangle(XSSFSheet sheet, int row1, int row2, int col1, int col2) {
        // left line
        drawVLine(sheet, row1, row2, col1);

        // top line
        drawHLine(sheet, row1, col1, col2);

        // bottom line
        drawHLine(sheet, row2, col1, col2);

        // right line
        drawVLine(sheet, row1, row2, col2);
    }

    public static void setCellValueAndStyle(Cell cell, String value, CellStyle style) {
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public static void setCellNumericValueAndStyle(Cell cell, double value, CellStyle style) {
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public static Cell createCell(XSSFSheet sheet, int row, int col) {
        Row row1 = sheet.createRow(row);
        Cell cell = row1.createCell(col);
        return cell;
    }

}
