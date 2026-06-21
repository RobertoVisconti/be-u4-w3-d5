package robertovisconti.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import robertovisconti.entities.ElementoCatalogo;
import robertovisconti.entities.Prestito;
import robertovisconti.exceptions.PrestitoNonTrovatoException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PrestitoDAO {

    private final EntityManager em;


    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    public void savePrestito(Prestito prestito) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(prestito);
            transaction.commit();
            System.out.println("Prestito avvenuto con successo.");
        } catch (Exception ex) {
            if (transaction.isActive()) transaction.rollback();
            System.out.println("Errore nel salvataggio prestito : " + ex.getMessage());
        }
    }

    public Prestito findById(UUID id) {
        Prestito prestito = em.find(Prestito.class, id);
        if (prestito == null) {
            throw new PrestitoNonTrovatoException("Nessun prestito trovato con ID: " + id);
        }
        return prestito;
    }

    // Ricerca tramite NUMERO TESSERA
    public List<ElementoCatalogo> findByTessera(String numeroTessera) {
        try {
            TypedQuery<ElementoCatalogo> query = em.createQuery("SELECT p.elementoCatalogo FROM Prestito p " +
                    "WHERE p.utente.numeroDiTessera = :tessera " +
                    "AND p.dataRestituzioneEffettiva IS NULL", ElementoCatalogo.class);
            query.setParameter("tessera", numeroTessera);
            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("Errore durante la ricerca tramite la tessera : " + numeroTessera + "la lista è vuota." + ex.getMessage());
            return List.of();

        }
    }

    public List<Prestito> findPrestitiScaduti() {
        try {
            TypedQuery<Prestito> query = em.createQuery("SELECT p FROM Prestito p " +
                    "WHERE p.dataRestituzionePrevista < :oggi " +
                    "AND p.dataRestituzioneEffettiva IS NULL", Prestito.class);
            query.setParameter("oggi", LocalDate.now());
            return query.getResultList();

        } catch (Exception ex) {
            System.out.println("Errore durante la ricerca dei prestiti scaduti " + ex.getMessage());
            return List.of();
        }
    }


}
