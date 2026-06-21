package robertovisconti.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import robertovisconti.enums.Periodicita;

@Entity
public class Riviste extends ElementoCatalogo {
    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    protected Riviste() {
    }

    public Riviste(String codiceIsbn, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(codiceIsbn, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    @Override
    public String toString() {
        return "Riviste{" +
                "periodicita=" + periodicita +
                "} " + super.toString();
    }
}
