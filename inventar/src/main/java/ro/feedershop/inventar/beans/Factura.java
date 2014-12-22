package ro.feedershop.inventar.beans;

import java.util.Map;

/**
 * O entitate reflecta informatia relevanta dintr-o factura in scopul inventarului.
 *
 * Created by nevastuica on 12/14/2014.
 */
public class Factura {

    String codFactura;
    String numeClient;
    String dataFactura;
    //<codProdus, cantitateProdus>
    Map<String, String> produseCumparate;

    public String getCodFactura() {
        return codFactura;
    }

    public void setCodFactura(String codFactura) {
        this.codFactura = codFactura;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public String getDataFactura() {
        return dataFactura;
    }

    public void setDataFactura(String dataFactura) {
        this.dataFactura = dataFactura;
    }

    public Map<String, String> getProduseCumparate() {
        return produseCumparate;
    }

    public void setProduseCumparate(Map<String, String> produseCumparate) {
        this.produseCumparate = produseCumparate;
    }
}
