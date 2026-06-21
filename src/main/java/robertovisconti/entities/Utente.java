package robertovisconti.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utente")

public class Utente {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(name = "data_di_nascita", nullable = false)
    private LocalDate dataDiNascita;

    @Column(name = "numero_di_tessera", unique = true, nullable = false)
    private String numeroDiTessera;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Prestito> prestiti = new ArrayList<>();

    protected Utente() {
    }

    public Utente(String nome, String cognome, LocalDate dataDiNascita, String numeroDiTessera) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.numeroDiTessera = numeroDiTessera;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public String getNumeroDiTessera() {
        return numeroDiTessera;
    }

    public List<Prestito> getPrestiti() {
        return prestiti;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", numeroDiTessera='" + numeroDiTessera + '\'' +
                ", prestiti=" + prestiti +
                '}';
    }
}
