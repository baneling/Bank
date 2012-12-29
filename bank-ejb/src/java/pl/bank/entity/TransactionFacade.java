package pl.bank.entity;


import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author carbolymer
 */
@Stateless
public class TransactionFacade extends AbstractFacade<Transaction> {
    
    public TransactionFacade() {
        super(Transaction.class);
    }
    
    @PersistenceContext(name="bank-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void save(Transaction transaction){
        if(transaction.getId() == null){
            em.persist(transaction);
        }else{
            em.merge(transaction); 
        }                 
    }
    
    public List<Transaction> findAll(){
            return em.createNamedQuery("getTransactions").getResultList();
    }
}
