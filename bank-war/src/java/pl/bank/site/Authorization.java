/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.site;

import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
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
    private Boolean isError = false;
    private String errorString;
    @EJB
    private UserFacade userFacade;
    
    private User user;
    /**
     * Creates a new instance of Authorization
     */
    public Authorization() {
        isError = false;
    }
    
    public String Login() throws NoSuchAlgorithmException
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        user = userFacade.findByUsernamePassword(login, password);
        session.setAttribute("user", user);
        if(user == null)
        {
            isError = true;
            errorString = "Nie ma takiego użytkownika";
            return "index?faces-redirect=true";
        }
        else if(user.getUserType() == UserType.ADMINISTRATOR)
        {
            return "administrator?faces-redirect=true";
        }
        else if(user.getUserType() == UserType.CASHIER)
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
     * @return the isError
     */
    public Boolean getIsError() {
        return isError;
    }

    /**
     * @param isError the isError to set
     */
    public void setIsError(Boolean isError) {
        this.isError = isError;
    }

    /**
     * @return the errorString
     */
    public String getErrorString() {
        return errorString;
    }

    /**
     * @param errorString the errorString to set
     */
    public void setErrorString(String errorString) {
        this.errorString = errorString;
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
    
    public void dupaDupa()
    {
        System.err.println("DUPA DUPA DUPA!!111");
    }
}
