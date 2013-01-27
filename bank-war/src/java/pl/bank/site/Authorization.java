/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.site;

import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import pl.bank.entity.User;
import pl.bank.entity.UserFacade;
import pl.bank.entity.UserType;

/**
 *
 * @author carbolymer
 */
@ManagedBean
@SessionScoped
public class Authorization {

    private String login;
    private String password;
    @EJB
    private UserFacade userFacade;
    
    private User user;
    /**
     * Creates a new instance of Authorization
     */
    public Authorization() {
    }
    
    public String Login() throws NoSuchAlgorithmException
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        user = userFacade.findByUsernamePassword(login, password);
        session.setAttribute("user", getUser());
        if(getUser() == null)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Niepoprawna nazwa użytkownika lub hasło!", ""));
            return "index";
        }
        else if(getUser().getUserType() == UserType.ADMINISTRATOR)
        {
            return "administrator?faces-redirect=true";
        }
        else if(getUser().getUserType() == UserType.CASHIER)
        {
            return "cashier?faces-redirect=true";
        }
        else
        {
            return "client?faces-redirect=true";
        }
    }
    public String Logout()
    {
        user = null;
        login = null;
        password = null;
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("user", null);
        return "index?faces-redirect=true";
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the userFacade
     */
    public UserFacade getUserFacade() {
        return userFacade;
    }

    /**
     * @param userFacade the userFacade to set
     */
    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }
}
