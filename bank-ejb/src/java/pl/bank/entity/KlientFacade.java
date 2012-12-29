package pl.bank.entity;


import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author carbolymer
 */
@Stateless
public class KlientFacade extends AbstractFacade<Klient> {
    
    public KlientFacade() {
        super(Klient.class);
    }
    
    @PersistenceContext(name="bank-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    
    public void save(Klient klient){
        if(klient.getId() == null){
            em.persist(klient);
        }else{
            em.merge(klient); 
        }                 
    }
    
    public List<Klient> search(String searchTerm){
        if(searchTerm != null){
            Query q = em.createNamedQuery("searchBlogs"); 
            q.setParameter("term", searchTerm);
            return q.getResultList();
        }else{
            return em.createNamedQuery("getBlogs").getResultList();
        }
        
    }
    
}
