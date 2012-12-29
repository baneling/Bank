/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.site;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import pl.bank.entity.User;
import pl.bank.entity.UserFacade;

/**
 *
 * @author carbolymer
 */
@ManagedBean
@RequestScoped
public class TestBean {
    @EJB
    private UserFacade userFacade;

    /**
     * Creates a new instance of TestBean
     */
    public TestBean() {
    }
    
    public void doSomething()
    {
        User u = new User();
        u.setName("name");
        u.setSurname("surname");
        u.setPassword("omgwtf");
//        userFacade.save(u);
        System.err.println("Something is done");
    }
}
