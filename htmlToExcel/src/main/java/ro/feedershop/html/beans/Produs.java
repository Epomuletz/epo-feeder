package ro.feedershop.html.beans;

/**
 * @author <a href="mailto:elena.banu@endava.com">Elena Banu</a>
 * @Revision $Rev$
 * @since 0.0.1
 */
public class Produs {

    String nume;
    String numeLinia2;
    String codIntern;
    String cantitate;
    String pret;
    String total;

    public String getNumeLinia2() {
        return numeLinia2;
    }

    public void setNumeLinia2(String numeLinia2) {
        this.numeLinia2 = numeLinia2;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCodIntern() {
        return codIntern;
    }

    public void setCodIntern(String codIntern) {
        this.codIntern = codIntern;
    }

    public String getCantitate() {
        return cantitate;
    }

    public void setCantitate(String cantitate) {
        this.cantitate = cantitate;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
