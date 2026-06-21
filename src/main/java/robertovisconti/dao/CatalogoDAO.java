package robertovisconti.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import robertovisconti.entities.ElementoCatalogo;
import robertovisconti.entities.Libri;
import robertovisconti.exceptions.ElementoDuplicatoException;
import robertovisconti.exceptions.ElementoNonTrovatoException;
import robertovisconti.exceptions.IsbnNonTrovatoException;

import java.util.List;

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

    // rimozione libro o rivista tramite ISBN
    public void deleteByIsbn(String isbn) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            ElementoCatalogo elemento = findByIsbn(isbn);
            if (elemento != null) {
                em.remove(elemento);
                transaction.commit();
                System.out.println("Elemento: " + elemento + " rimosso con successo.");
            } else {
                if (transaction.isActive()) transaction.rollback();
                throw new IsbnNonTrovatoException(isbn);
            }
        } catch (IsbnNonTrovatoException ex) {
            throw ex;
        } catch (Exception ex) {
            if (transaction.isActive()) transaction.rollback();
            System.out.println("Errore durante la rimozione: " + ex.getMessage());
        }
    }

    // Ricerca tramite AnnoPubblicazione
    public List<ElementoCatalogo> findByAnnoP(int anno) {
        TypedQuery<ElementoCatalogo> query = em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno", ElementoCatalogo.class);
        query.setParameter("anno", anno);
        List<ElementoCatalogo> result = query.getResultList();
        if (result.isEmpty()) {
            throw new ElementoNonTrovatoException("Nessun elemento trovato per l'anno di pubblicazione: " + anno);
        }
        return result;
    }

    // Ricerca tramite Autore
    public List<Libri> findByAutore(String autore) {
        TypedQuery<Libri> query = em.createQuery("SELECT l FROM Libri l WHERE LOWER(l.autore) = LOWER(:autore)", Libri.class);
        query.setParameter("autore", autore);
        List<Libri> result = query.getResultList();
        if (result.isEmpty()) {
            throw new ElementoNonTrovatoException("Nessun libro trovato per l'autore: " + autore);
        }
        return result;
    }

    // Ricerca tramite titolo
    public List<ElementoCatalogo> findByTitolo(String titolo) {
        TypedQuery<ElementoCatalogo> query = em.createQuery("SELECT e FROM ElementoCatalogo e WHERE LOWER(e.titolo) LIKE LOWER(:titolo)", ElementoCatalogo.class);
        query.setParameter("titolo", "%" + titolo + "%");
        List<ElementoCatalogo> result = query.getResultList();
        if (result.isEmpty()) {
            throw new ElementoNonTrovatoException("Nessun elemento trovato con il titolo: " + titolo);
        }
        return result;
    }
}
