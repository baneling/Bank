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
public class UserFacade extends AbstractFacade<User> {
    
    public UserFacade() {
        super(User.class);
    }
    
    @PersistenceContext(name="bank-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void save(User client){
        if(client.getId() == null){
            em.persist(client);
        }else{
            em.merge(client); 
        }                 
    }
    
    public List<User> findByUsername(String searchTerm){
        if(searchTerm != null){
            Query q = em.createNamedQuery("findUsers"); 
            q.setParameter("term", searchTerm);
            return q.getResultList();
        }else{
            return em.createNamedQuery("getUsers").getResultList();
        }
        
    }
    
}
