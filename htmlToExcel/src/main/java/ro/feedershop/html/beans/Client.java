package ro.feedershop.html.beans;

/**
 * @author <a href="mailto:elena.banu@endava.com">Elena Banu</a>
 * @Revision $Rev$
 * @since 0.0.1
 */
public class Client {

    String numeComplet;
    String email;
    Adresa adresaFacturare;
    Adresa adresaLivrare;

    public String getNumeComplet() {
        return numeComplet;
    }

    public void setNumeComplet(String numeComplet) {
        this.numeComplet = numeComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Adresa getAdresaFacturare() {
        return adresaFacturare;
    }

    public void setAdresaFacturare(Adresa adresaFacturare) {
        this.adresaFacturare = adresaFacturare;
    }

    public Adresa getAdresaLivrare() {
        return adresaLivrare;
    }

    public void setAdresaLivrare(Adresa adresaLivrare) {
        this.adresaLivrare = adresaLivrare;
    }
}
