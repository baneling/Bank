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
public class AccountFacade extends AbstractFacade<Account> {
    
    public AccountFacade() {
        super(Account.class);
    }
    
    @PersistenceContext(name="bank-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void save(Account account){
        if(account.getId() == null){
            em.persist(account);
        }else{
            em.merge(account); 
        }                 
    }
    
    public List<Account> getAll(){
        return em.createNamedQuery("getAccounts").getResultList();
    }   
     
    public Account getByNumber(Long number){
        Query q = em.createNamedQuery("findByNumber"); 
        q.setParameter("number", number);
        try {
            return (Account)q.getSingleResult(); 
        } catch (javax.persistence.NoResultException ex) {
            return null;
        }        
    }
    
}
