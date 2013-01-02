package pl.bank.entity;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    
    public User findByUsernamePassword(String login, String password) throws NoSuchAlgorithmException
    {
        User u;
        Query q = em.createNamedQuery("findUserLoginPassword"); 
        q.setParameter("login", login);
        q.setParameter("password", sha512(password));
        try{
            u = (User)q.getSingleResult();
        }
        catch(NoResultException e)
        {
            u = null;
        }
        return u;
    }
    
    public static String sha512(String text) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(text.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

    	return sb.toString();
    }
}
