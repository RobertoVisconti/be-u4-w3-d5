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
    private String codiceIsbn;

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
        this.codiceIsbn = codiceISBN;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;

    }

    public UUID getId() {
        return id;
    }

    public String getCodiceISBN() {
        return codiceIsbn;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public String getTitolo() {
        return titolo;
    }

    @Override
    public String toString() {
        return "ElementoCatalogo{" +
                "id=" + id +
                ", codiceIsbn='" + codiceIsbn + '\'' +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", numeroPagine=" + numeroPagine +
                '}';
    }
}
