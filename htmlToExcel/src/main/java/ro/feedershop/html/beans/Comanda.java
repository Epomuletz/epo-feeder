package ro.feedershop.html.beans;

import java.util.List;
import java.util.StringTokenizer;

/**
 * @author <a href="mailto:elena.banu@endava.com">Elena Banu</a>
 * @Revision $Rev$
 * @since 0.0.1
 */
public class Comanda {

    String numar;
    String dataComanda;
    String situatie;
    Client client;
    List<Produs> produseComandate;
    Double pretTotal;
    Double livrareManipulare;

    public Double getLivrareManipulare() {
        return livrareManipulare;
    }

    public void setLivrareManipulare(Double livrareManipulare) {
        this.livrareManipulare = livrareManipulare;
    }

    public Double getPretTotal() {
        return pretTotal;
    }

    public void setPretTotal(Double pretTotal) {
        this.pretTotal = pretTotal;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public String getDataComanda() {
        StringTokenizer st = new StringTokenizer(dataComanda, " ");
        int count = 0;
        String data = "";
        while(st.hasMoreTokens() && count < 3){
            data += st.nextToken();
            count++;
        }
        return data;
    }

    public void setDataComanda(String dataComanda) {
        this.dataComanda = dataComanda;
    }

    public String getSituatie() {
        return situatie;
    }

    public void setSituatie(String situatie) {
        this.situatie = situatie;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Produs> getProduseComandate() {
        return produseComandate;
    }

    public void setProduseComandate(List<Produs> produseComandate) {
        this.produseComandate = produseComandate;
    }
}
