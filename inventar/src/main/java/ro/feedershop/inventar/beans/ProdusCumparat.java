package ro.feedershop.inventar.beans;

/**
 * Reflacta o entitate cumparata.
 * O factura = o lista de produseCumparate
 * Un excel de inventar = Map<CodProdus, List<ProdusCumparat>>
 *
 * Created by nevastuica on 12/13/2014.
 */
public class ProdusCumparat {

    String numeClient;
    String codFactura;
    String dataFactura;
    String codProdus;
    String cantitate;

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public String getCodFactura() {
        return codFactura;
    }

    public void setCodFactura(String codFactura) {
        this.codFactura = codFactura;
    }

    public String getDataFactura() {
        return dataFactura;
    }

    public void setDataFactura(String dataFactura) {
        this.dataFactura = dataFactura;
    }

    public String getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(String codProdus) {
        this.codProdus = codProdus;
    }

    public String getCantitate() {
        return cantitate;
    }

    public void setCantitate(String cantitate) {
        this.cantitate = cantitate;
    }
}
