package ro.feedershop.html.util;

import ro.feedershop.html.beans.Adresa;
import ro.feedershop.html.beans.Client;
import ro.feedershop.html.beans.Comanda;
import ro.feedershop.html.beans.Produs;
import ro.feedershop.utile.StringUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @Revision $Rev$
 * @since 0.0.1
 */
public class ComandaUtil {

    public static Comanda populareComanda(String copied)
    {
        int index1 = copied.indexOf(ConstanteUtil.COMANDA_START);
        int index2 = copied.indexOf(ConstanteUtil.COMANDA_END);
        String comanda = copied.substring(index1, index2);
        StringTokenizer st = new StringTokenizer(comanda, "\n");

        Comanda comandaObj = new Comanda();
        comandaObj.setNumar(st.nextToken());
        st.nextToken();
        comandaObj.setDataComanda(st.nextToken());
        st.nextToken();
        comandaObj.setSituatie(st.nextToken());
        comandaObj.setClient(getClient(copied));
        comandaObj.setProduseComandate(populareProduse(copied));
        comandaObj.setPretTotal(calculateTotal(comandaObj));
        return comandaObj;
    }

    private static Double calculateTotal(Comanda comanda) {
        List<Produs> produse = comanda.getProduseComandate();
        Double sum = new Double(0.00);
        for (Produs produs : produse) {
            Double totalPartial = Double.valueOf(produs.getTotal());
            sum += totalPartial;
        }
        DecimalFormat decimalFormat = new DecimalFormat("00.00");
        String sumStr = decimalFormat.format(sum);

        return Double.valueOf(sumStr);
    }



    private static String formatPret(String pret) {
        pret = pret.replace("RON", "");
        pret = pret.replace(",", ".");
        if (StringUtil.nrOccurences(pret, ".") > 1) {
            pret = StringUtil.removeExtraPoint(pret);
        }
        return pret;
    }

    private static Produs formatareProdus(Produs produs) {

//        cod intern
        String cod = produs.getCodIntern();
        cod = cod.replace(ConstanteUtil.COD_INTERN, "");
        produs.setCodIntern(cod);

        //  pret
        produs.setPret(formatPret(produs.getPret()));
        produs.setTotal(formatPret(produs.getTotal()));

        //cantitate
        String cantitate = produs.getCantitate();
        produs.setCantitate(cantitate.substring(8));

        //total
        String total = produs.getTotal();
        int index = total.lastIndexOf("\t");
        String tempTotal = total.substring(index + 1);
        if (StringUtil.nrOccurences(tempTotal, ".") > 1) {
            produs.setTotal(StringUtil.removeExtraPoint(tempTotal));
        } else {
            produs.setTotal(tempTotal);
        }


        //denumire
        final int MAX_LEN = 45;
        String denumire = produs.getNume();
        if (denumire.length() > MAX_LEN) {
            StringTokenizer st = new StringTokenizer(denumire, " ");
            List<String> denList = StringUtil.getListFromStringTokenizer(st);
            StringBuilder builder = new StringBuilder();
            StringBuilder builder2 = new StringBuilder();
            boolean isFirst = true;
            int futureLen = 0;
            for (String den : denList) {
                futureLen += den.length();
                if (futureLen > MAX_LEN || builder2.length() > 0) {
                    if (isFirst) {
                        produs.setNume(builder.toString());
                        isFirst = false;
                    }
                    builder2.append(den);
                    builder2.append(" ");
                    futureLen = builder2.toString().length();
                } else {

                    builder.append(den);
                    builder.append(" ");
                    futureLen = builder.toString().length();
                }
            }
            produs.setNumeLinia2(builder2.toString());
        }

        return produs;
    }



    public static List<Produs> populareProduse(String copied) {
        List<Produs> produse = new ArrayList<Produs>();

        int index1 = copied.indexOf(ConstanteUtil.PROD_START);
        int index2 = copied.lastIndexOf(ConstanteUtil.PROD_END);
        String produseStr = copied.substring(index1, index2);
        StringTokenizer st = new StringTokenizer(produseStr, "\n");

        List<String> prods = StringUtil.getListFromStringTokenizer(st);

        int count = 1;
        while (count < prods.size()) {
            Produs produs = new Produs();
            produs.setNume(prods.get(count++));
            produs.setCodIntern(prods.get(count++));
            produs.setPret(prods.get(++count));
            produs.setCantitate(prods.get(++count));
            while (!prods.get(count).contains("RON")) {
                count++;
            }
            produs.setTotal(prods.get(++count));
            count++;
            produs = formatareProdus(produs);
            produse.add(produs);
        }

        return produse;
    }

    private static Adresa getAdress(String copied, String const1, String const2) {

        int index1 = copied.indexOf(const1);
        int index2 = copied.lastIndexOf(const2);
        String adresa = copied.substring(index1, index2);
        StringTokenizer st2 = new StringTokenizer(adresa, "\n");

        st2.nextToken();
        st2.nextToken();
        Adresa adresaObj = new Adresa();
        adresaObj.setLinia1(st2.nextToken());
        adresaObj.setLinia3(st2.nextToken());
        st2.nextToken();
        adresaObj.setTelefon(st2.nextToken());

        int MAX_LEN = 35;
        String adrLin = adresaObj.getLinia1();
        if (adrLin.length() > MAX_LEN) {
            StringTokenizer st = new StringTokenizer(adrLin, " ");
            List<String> denList = StringUtil.getListFromStringTokenizer(st);
            StringBuilder builder = new StringBuilder();
            StringBuilder builder2 = new StringBuilder();
            boolean isFirst = true;
            for (String den : denList) {
                int futureLen = builder.toString().length() + den.length();
                if (futureLen > MAX_LEN) {
                    if (isFirst) {
                        adresaObj.setLinia1(builder.toString());
                        isFirst = false;
                    }
                    builder2.append(den);
                    builder2.append(" ");
                } else {
                    builder.append(den);
                    builder.append(" ");
                }

            }
            adresaObj.setLinia2(builder2.toString());
        }
        return adresaObj;
    }

    public static Client getClient(String copied) {

        int index = copied.indexOf(ConstanteUtil.NUME_CLIENT);
        int index2 = copied.lastIndexOf(ConstanteUtil.GRUP_CLIENT);
        String nume = copied.substring(index, index2);
        StringTokenizer st = new StringTokenizer(nume, "\n");

        st.nextToken();
        Client client = new Client();
        client.setNumeComplet(st.nextToken());
        st.nextToken();
        client.setEmail(st.nextToken());

//        Adrese
        client.setAdresaFacturare(getAdress(copied, ConstanteUtil.ADRESA_FACTURARE, ConstanteUtil.ADRESA_LIVRARE));
        client.setAdresaLivrare(getAdress(copied, ConstanteUtil.ADRESA_LIVRARE, ConstanteUtil.INFO_PLATA));
        return client;
    }

}
