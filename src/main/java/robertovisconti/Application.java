package robertovisconti;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import robertovisconti.dao.CatalogoDAO;
import robertovisconti.dao.PrestitoDAO;
import robertovisconti.dao.UtenteDAO;
import robertovisconti.entities.*;
import robertovisconti.enums.Periodicita;
import robertovisconti.exceptions.ElementoNonTrovatoException;
import robertovisconti.exceptions.IsbnNonTrovatoException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Application {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("be-u4-w3-d5");

    public static void main(String[] args) {

        EntityManager em = entityManagerFactory.createEntityManager();

        // DAO
        CatalogoDAO catalogoDAO = new CatalogoDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);


        // METODO SAVE LIBRI E RIVISTE

        Libri lb1 = new Libri("9788863552243", "La storia dell'animazione giapponese", 2012, 608, "Guido Tavassi", "Saggistica");
        Libri lb2 = new Libri("9788831003780", "Il mondo di Miyazaki", 2020, 310, "Susan Napier", "Cinema");
        Libri lb3 = new Libri("9788807883996", "Autostop con Buddha", 2014, 448, "Will Ferguson", "Viaggi");

        Riviste rv1 = new Riviste("9772039411005", "Japan Magazine - Focus Otaku", 2026, 96, Periodicita.MENSILE);
        Riviste rv2 = new Riviste("9771120533002", "Quaderni d'Asia e Cultura Orientale", 2025, 210, Periodicita.SEMESTRALE);
        Riviste rv3 = new Riviste("9772039411012", "Ghibli World & J-Culture", 2026, 120, Periodicita.SETTIMANALE);

//        catalogoDAO.save(lb1);
//        catalogoDAO.save(lb2);
//        catalogoDAO.save(lb3);
//        catalogoDAO.save(rv1);
//        catalogoDAO.save(rv2);
//        catalogoDAO.save(rv3);


//         METODO RICERCA ISBN

        String isbnRicerca = "9772039411012";

        ElementoCatalogo elementoTrovato = catalogoDAO.findByIsbn(isbnRicerca);
        if (elementoTrovato != null) {
            System.out.println("Elemento trovato nel db: " + elementoTrovato);
        } else {
            throw new IsbnNonTrovatoException(isbnRicerca);
        }


        // METODO DELETE ISBN

//        String isbnDelete = "9771120533002";
//
//        try {
//            catalogoDAO.deleteByIsbn(isbnDelete);
//            System.out.println("Elemento rimosso con successo.");
//        } catch (IsbnNonTrovatoException ex) {
//            System.out.println("Errore nella ricerca del ISBN: " + ex.getMessage());
//        } catch (Exception ex) {
//            System.out.println("Errore imprevisto: " + ex.getMessage());
//        }


        // METODO RICERCA ANNO

        try {
            List<ElementoCatalogo> elementi = catalogoDAO.findByAnnoP(2014);
            elementi.forEach(elemento -> System.out.println("Elemento trovati: " + elemento.getClass().getSimpleName() + " : " + elemento.getTitolo()));
        } catch (ElementoNonTrovatoException ex) {
            System.out.println(ex.getMessage());
        }


        // METODO RICERCA AUTORE

        try {
            List<Libri> libriAutore = catalogoDAO.findByAutore("Susan Napier");
            libriAutore.forEach(libri -> System.out.println("Elemento trovato: " + libri.getTitolo()));
        } catch (ElementoNonTrovatoException ex) {
            System.out.println(ex.getMessage());
        }

        // METODO RICERCA TITOLO

        try {
            List<ElementoCatalogo> titolo = catalogoDAO.findByTitolo("La storia");
            titolo.forEach(elemento -> System.out.println("Elemento trovato: " + elemento.getTitolo()));
        } catch (ElementoNonTrovatoException ex) {
            System.out.println(ex.getMessage());
        }


        // CREAZIONE E  SAVE UTENTE
        Utente u1 = new Utente("Mario", "Rossi", LocalDate.of(1990, 5, 12), "BIB-2024-0982A");
        Utente u2 = new Utente("Luigi", "Verdi", LocalDate.of(1985, 8, 20), "BIB-2025-1143B");
        Utente u3 = new Utente("Anna", "Bianchi", LocalDate.of(1995, 3, 15), "BIB-2026-4401X");
        Utente u4 = new Utente("Sofia", "Franchi", LocalDate.of(2000, 11, 2), "BIB-2023-8829C");
        Utente u5 = new Utente("Marco", "Neri", LocalDate.of(1992, 1, 30), "BIB-2026-3190F");

//        utenteDAO.save(u1);
//        utenteDAO.save(u2);
//        utenteDAO.save(u3);
//        utenteDAO.save(u4);
//        utenteDAO.save(u5);

        // CREAZIONE E SAVE PRESTITO

        ElementoCatalogo lbTrovato1 = catalogoDAO.findByIsbn("9788831003780");
        ElementoCatalogo lbTrovato2 = catalogoDAO.findByIsbn("9771120533002");

        Utente uTrovato1 = utenteDAO.findById(UUID.fromString("264a8142-4f2d-45de-a0e0-80b49c1dba97"));
        Utente uTrovato2 = utenteDAO.findById(UUID.fromString("8604ec1f-d25c-4f1d-be75-9c1e31004c5f"));


        Prestito p1 = new Prestito(uTrovato1, lbTrovato1);


        Prestito p2 = new Prestito(uTrovato2, lbTrovato2);
//        p2.setDataInizioPrestito(LocalDate.now().minusDays(45));
//        p2.setDataRestituzionePrevista(p2.getDataInizioPrestito().plusDays(30));

//        prestitoDAO.savePrestito(p1);
//        prestitoDAO.savePrestito(p2);


        // RICERCA NUMERO TESSERA

        List<ElementoCatalogo> inPrestito = prestitoDAO.findByTessera(u2.getNumeroDiTessera());
        inPrestito.forEach(elementoCatalogo -> System.out.println(" Libri trovati in prestito: " + elementoCatalogo.getTitolo()));


        // RICERCA LIBRI IN PRESTITO

        List<Prestito> inPrestito2 = prestitoDAO.findPrestitiScaduti();
        inPrestito2.forEach(prestito -> System.out.println("Libri in prestito scaduti o non restituiti: " + prestito.getElementoCatalogo().getTitolo() + " - Utente: " + prestito.getUtente().getNome()));


    }


}
