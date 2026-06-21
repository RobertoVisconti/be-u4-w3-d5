package robertovisconti.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import robertovisconti.entities.Utente;
import robertovisconti.exceptions.ElementoNonTrovatoException;
import robertovisconti.exceptions.UtenteGiàSalvato;

import java.util.UUID;

public class UtenteDAO {

    private final EntityManager em;


    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Utente utente) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(utente);
            transaction.commit();
            System.out.println("Utente salvato con successo");
        } catch (PersistenceException ex) {
            if (transaction.isActive()) transaction.rollback();
            throw new UtenteGiàSalvato("L'utente che stai inserendo si trova già nel DB.");

        } catch (Exception ex) {
            if (transaction.isActive()) transaction.rollback();
            System.out.println("Errore nel salvataggio utente : " + ex.getMessage());
        }
    }

    public Utente findById(UUID id) {
        Utente utente = em.find(Utente.class, id);
        if (utente == null) {
            throw new ElementoNonTrovatoException("Nessun utente trovato con ID: " + id);
        }
        return utente;
    }
}
