package robertovisconti.entities;


import jakarta.persistence.Entity;

@Entity
public class Libri extends ElementoCatalogo {

    private String autore;
    private String genere;

    protected Libri() {
    }

    public Libri(String codiceIsbn, String titolo, int annoPubblicazione, int numeroPagine, String autore, String genere) {
        super(codiceIsbn, titolo, annoPubblicazione, numeroPagine);
        this.autore = autore;
        this.genere = genere;
    }

    public String getAutore() {
        return autore;
    }

    public String getGenere() {
        return genere;
    }

    @Override
    public String toString() {
        return "Libri{" +
                "autore='" + autore + '\'' +
                ", genere='" + genere + '\'' +
                "} " + super.toString();
    }
}
