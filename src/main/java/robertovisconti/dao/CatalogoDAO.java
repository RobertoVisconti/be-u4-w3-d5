package robertovisconti.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import robertovisconti.entities.ElementoCatalogo;
import robertovisconti.exceptions.ElementoDuplicatoException;

public class CatalogoDAO {
    private final EntityManager em;


    public CatalogoDAO(EntityManager em) {
        this.em = em;
    }

    // Aggiunta di un libro o rivista
    public void save(ElementoCatalogo elemento) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(elemento);
            transaction.commit();
            System.out.println("Elemento salvato con successo.");
        } catch (PersistenceException ex) {
            if (transaction.isActive()) transaction.rollback();
            throw new ElementoDuplicatoException("L'elemento che stai inserendo è già presente nel catalogo. " + ex.getMessage());
        } catch (Exception ex) {
            if (transaction.isActive()) transaction.rollback();
            System.out.println("Errore durante il salvataggio: " + ex.getMessage());
        }
    }

    // ricerca tramite ISBN
    public ElementoCatalogo findByIsbn(String isbn) {
        TypedQuery<ElementoCatalogo> query = em.createQuery("SELECT  e FROM  ElementoCatalogo e WHERE e.codiceIsbn = :isbn", ElementoCatalogo.class);
        query.setParameter("isbn", isbn);
        return query.getResultStream().findFirst().orElse(null);
    }


    //
}
