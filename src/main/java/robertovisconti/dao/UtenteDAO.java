package robertovisconti.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import robertovisconti.entities.Utente;

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
        } catch (Exception ex) {
            if (transaction.isActive()) transaction.rollback();
            System.out.println("Errore nel salvataggio utente : " + ex.getMessage());
        }
    }
}
