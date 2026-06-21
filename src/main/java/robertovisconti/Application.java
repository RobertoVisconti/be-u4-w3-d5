package robertovisconti;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import robertovisconti.dao.CatalogoDAO;
import robertovisconti.entities.ElementoCatalogo;
import robertovisconti.entities.Libri;
import robertovisconti.entities.Riviste;
import robertovisconti.enums.Periodicita;
import robertovisconti.exceptions.IsbnNonTrovatoException;

public class Application {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("be-u4-w3-d5");

    public static void main(String[] args) {

        EntityManager em = entityManagerFactory.createEntityManager();

        // DAO
        CatalogoDAO catalogoDAO = new CatalogoDAO(em);


        // METODO SAVE

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


        // METODO RICERCA ISBN

        String isbnRicerca = "9772039411012";

        ElementoCatalogo elementoTrovato = catalogoDAO.findByIsbn(isbnRicerca);
        if (elementoTrovato != null) {
            System.out.println("Elemento trovato nel db: " + elementoTrovato);
        } else {
            throw new IsbnNonTrovatoException(isbnRicerca);
        }


    }
}
