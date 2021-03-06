package ro.feedershop.inventar.services;

import org.apache.poi.POIXMLException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import ro.feedershop.inventar.beans.Factura;
import ro.feedershop.utile.Constante;
import ro.feedershop.utile.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Service in care culeg informatia despre produsele cumparate din fisierele de facturi.
 * <p/>
 * Created by nevastuica on 12/13/2014.
 */
public class FacturaService {

    /**
     * Incepand cu februarie sunt cele generate dinainte
     *
     * @param fisFact
     */
    public Factura readFactInfoFromExcel(File fisFact) {
        FileInputStream fis;
        Factura factura = new Factura();
        try {
            fis = new FileInputStream(fisFact);
            XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);

            factura = extragInfoFactura(fisFact);
            factura = getProductMap(factura, sheet);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (POIXMLException e) {
            RaportService.scrieInRaport("Fisierul " + fisFact.getName() + " nu poate fi deschis");
//            e.printStackTrace();
        }

        return factura;
    }


    /**
     * Construiesc map-ul <codProdus, cantitate> din factura excel.
     * Repere: Coloanele 'Cod Produs' si 'Cantitate' si randul cu 'Intocmit de'
     *
     * @param factura - obiectul in care va fi stocat map-ul
     * @param sheet   - sheetul de excel cu factura fiscala
     * @return
     */
    Factura getProductMap(Factura factura, XSSFSheet sheet) {
        Map<String, String> produseCumparate = new HashMap<String, String>();
        List<String> repereList = new ArrayList<String>();

        //repere
        int repere = 0;
        int codProdCol = 0;
        int cantCol = 0;

        String produsKey = "";
        String cantVal = "";
        int pereche = 0;

        int rowsCount = sheet.getLastRowNum();
        for (int i = 0; i <= rowsCount; i++) {
            if (repere == 3) {
                break;
            }
            Row row = sheet.getRow(i);
            if (row != null) {
                int colCounts = row.getLastCellNum();
                for (int j = 0; j < colCounts; j++) {
                    if (repere == 3) {
                        break;
                    }
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING: {
                                String val = cell.getStringCellValue();
                                if (val.contains(Constante.COD)) {
                                    if (!repereList.contains(val)) {
                                        repereList.add(val);
                                        repere++;
                                    }
                                    codProdCol = j;

                                } else if (val.contains(Constante.CANTITATE)) {
                                    if (!repereList.contains(val)) {
                                        repereList.add(val);
                                        repere++;
                                    }
                                    cantCol = j;
                                } else if (val.contains(Constante.INTOCMIT)) {
                                    codProdCol = j;
                                    repere++;
                                } else if (repere == 2 && j == codProdCol) {
                                    produsKey = val;
                                    pereche++;
                                } else if (repere == 2 && j == cantCol) {
                                    cantVal = val;
                                    pereche++;
                                }
                                break;
                            }
                            case Cell.CELL_TYPE_NUMERIC: {
                                double val = cell.getNumericCellValue();
                                if (repere == 2 && j == codProdCol) {
                                    produsKey = "" + val;
                                    pereche++;
                                } else if (repere == 2 && j == cantCol) {
                                    cantVal = "" + val;
                                    pereche++;
                                }
                                break;
                            }
                            default:
                        }//end switch
                    } //end if cell
                } //end for cols
                if (pereche == 2) {
                    produseCumparate.put(produsKey, cantVal);
                    pereche = 0;
                }
            }//end null check row
        }
        factura.setProduseCumparate(produseCumparate);
        return factura;
    }

    /**
     * @param factFile
     * @return
     */
    Factura extragInfoFactura(File factFile) {
        String fileName = factFile.getName();
        RaportService.scrieInRaport("FileName:  " + fileName);

        StringTokenizer st = new StringTokenizer(fileName, "_");
        List<String> info = StringUtil.getListFromStringTokenizer(st);
        Factura factura = new Factura();
        String numeClient = "";
        int i = 4;
        try {
            factura.setCodFactura(info.get(1));
            factura.setDataFactura(info.get(2));
            numeClient = info.get(3);

            numeClient = numeClient.substring(0, numeClient.indexOf("."));

        } catch (StringIndexOutOfBoundsException e) {
            RaportService.scrieInRaport("Nume client prost format: " + numeClient);
            String nume = info.get(i);
            while(nume.indexOf(".") == -1)
            {
                numeClient += nume;
                i++;
                nume = info.get(i);
            }
            numeClient += nume.substring(0, nume.indexOf("."));
            RaportService.scrieInRaport("Dupa corectie: " + numeClient);

        } catch (IndexOutOfBoundsException ioobe) {
            RaportService.scrieInRaport("Numele facturii nu poate fi tokenizat: " + fileName);
        }

        factura.setNumeClient(numeClient);
        return factura;
    }

    /**
     * Extrag data facturii din shape-ul central al fact proforme
     *
     * @param sheet
     * @return
     */
    String extragDataFactVarIanuarie(XSSFSheet sheet) {
        final String dataPattern = "Data:";
        final String endTag = "</a:t>";
        String dataFact = "";

        XSSFDrawing draw = sheet.createDrawingPatriarch();
        List<XSSFShape> shapes = draw.getShapes();
        Iterator<XSSFShape> it = shapes.iterator();

        while (it.hasNext()) {
            XSSFShape shape = it.next();
            if (shape instanceof XSSFSimpleShape) {
                XSSFSimpleShape textbox = (XSSFSimpleShape) shape;
                String text = textbox.getCTShape().getTxBody().toString();
                if (text.contains(dataPattern)) {
                    int start = text.indexOf(dataPattern);
                    text = text.substring(start);
                    int end = text.indexOf(endTag);
                    dataFact = text.substring(dataPattern.length(), end);
                }
            }
        }
        return dataFact;
    }
}
