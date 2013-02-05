/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.site;

import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import pl.bank.entity.User;
import pl.bank.entity.UserFacade;
import pl.bank.entity.UserType;

/**
 *
 * @author carbolymer
 */
@ManagedBean
@RequestScoped
public class NewClient {
    private String login;
    private String name;
    private String surname;
    private String address;
    private String password;
    @EJB
    private UserFacade userFacade; 
    @ManagedProperty("#{userList}")
    private UserList userList;
    /**
     * Creates a new instance of NewClient
     */
    public NewClient() {
    }
    
    public void save(ActionEvent event) throws NoSuchAlgorithmException
    {
        FacesMessage message = null;
        if(userFacade.findByLogin(login).size() > 0)
             message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Taki login już istnieje", "");
        else
        {
            User user = new User();
            user.setLogin(login);
            user.setName(name);
            user.setSurname(surname);
            user.setAddress(address);
            user.setPassword(password);
            user.setUserType(UserType.CLIENT);
            userFacade.save(user);
            reset(event);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Konto zostało pomyślnie założone.", "");
            userList.getUsers(true);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void reset(ActionEvent event)
    {
        login = "";
        name = "";
        surname = "";
        address = "";
        password = "";
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return the userList
     */
    public UserList getUserList() {
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public void setUserList(UserList userList) {
        this.userList = userList;
    }
}
