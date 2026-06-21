package robertovisconti.entities;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "elemento_catalogo")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ElementoCatalogo {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "codice_isbn", unique = true, nullable = false)
    private String codiceISBN;

    @Column(nullable = false)
    private String titolo;

    @Column(name = "anno_pubblicazione", nullable = false)
    private int annoPubblicazione;

    @Column(name = "numero_pagine", nullable = false)
    private int numeroPagine;

    protected ElementoCatalogo() {
    }

    public ElementoCatalogo(String codiceISBN, String titolo, int annoPubblicazione, Integer numeroPagine) {
        this.id = id;
        this.titolo = titolo;
        this.codiceISBN = codiceISBN;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;

    }

    public UUID getId() {
        return id;
    }

    public String getCodiceISBN() {
        return codiceISBN;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    @Override
    public String toString() {
        return "ElementoCatalogo{" +
                "id=" + id +
                ", codiceISBN='" + codiceISBN + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", numeroPagine=" + numeroPagine +
                '}';
    }
}
