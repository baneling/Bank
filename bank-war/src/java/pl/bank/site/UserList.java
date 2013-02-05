/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.site;

import java.awt.geom.Arc2D;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;
import pl.bank.entity.Account;
import pl.bank.entity.AccountFacade;
import pl.bank.entity.Transaction;
import pl.bank.entity.TransactionFacade;
import pl.bank.entity.User;
import pl.bank.entity.UserFacade;
import pl.bank.entity.UserType;

/**
 *
 * @author carbolymer
 */
@ManagedBean
@RequestScoped
public class UserList implements Serializable{
    @EJB
    private TransactionFacade transactionFacade;
    @EJB
    private AccountFacade accountFacade;
    @EJB
    private UserFacade userFacade;    
    @ManagedProperty("#{authorization}")
    private Authorization authorizationBean; 
    private List<User> users = new ArrayList<User>();;
    
    @ManagedProperty("#{container}")
    private Container container;
    
    private List<User> filteredUsers = new ArrayList<User>();
    /**
     * Creates a new instance of UserList
     */
    public UserList() {
    }

    /**
     * @return the users
     */
    public List<User> getUsers()
    {
        return getUsers(false);
    }
    
    public List<User> getUsers(boolean forceReload) {
        if (users.size() > 0 && !forceReload) {
            return users;
        }
        if (authorizationBean.getUser().getUserType() == UserType.CASHIER) {
            users = userFacade.findByType(UserType.CLIENT);
        } else if (authorizationBean.getUser().getUserType() == UserType.ADMINISTRATOR) {
            users = userFacade.findByLogin(null);
        } else {
            users = new ArrayList<User>();
        }
        filteredUsers = users;
        return users;
    }
    
    

    /**
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * @return the authorizationBean
     */
    public Authorization getAuthorizationBean() {
        return authorizationBean;
    }

    /**
     * @param authorizationBean the authorizationBean to set
     */
    public void setAuthorizationBean(Authorization authorizationBean) {
        this.authorizationBean = authorizationBean;
    }

    /**
     * @return the filteredUsers
     */
    public List<User> getFilteredUsers() {
//        return users;
        return filteredUsers;
    }

    /**
     * @param filteredUsers the filteredUsers to set
     */
    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }
    
    public void onEdit(RowEditEvent event) {
        User user = (User) event.getObject();
        FacesMessage msg;
        boolean isValid = true;
        if(userFacade.findByLogin(user.getLogin()).size() > 0)
        {
            if(userFacade.findByLogin(user.getLogin()).get(0).getId().longValue() != user.getId().longValue())
            {
                isValid = false;
            }
        }
        if(isValid)
        {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sukces!", "Użytkownik został zmodyfikowany.");
            userFacade.save(user);
            getUsers(true);
        }
        else
        {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Błąd!", "Taki login już istnieje.");
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
      
    public void onCancel(RowEditEvent event) {  
    }
    
    public void deleteAccount(ActionEvent event) throws IOException
    {
        Account account = accountFacade.getByNumber((Long)event.getComponent().getAttributes().get("accountNumber"));
        if(account == null)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Błąd", "Nie można było usunąć konta o numerze "+account.getNumber()+"."));
            return;
        }
        Set<Transaction> trs = account.getTransactions();
        for(Transaction transaction : trs)
        {
            transactionFacade.remove(transaction);
        }
        accountFacade.remove(account);
        getUsers(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sukces", "Konto o numerze "+account.getNumber()+" zostało usunięte."));
    }

    public void addNewAccount(ActionEvent event)
    {
        User user = userFacade.findById((Long)event.getComponent().getAttributes().get("userId"));
        long signature = (long)1e8;
        long number = 0l;
        Account account;
        do
        {
           number = signature+(long)(Math.random()*(signature/100));
           account = accountFacade.getByNumber(number);
        }
        while(account != null);
        account = new Account();
        account.setBalance(0);
        account.setUser(user);
        account.setNumber(number);
        accountFacade.save(account);
        getUsers(true);
    }

    public SelectItem[] getUserTypes() {
        SelectItem[] items = new SelectItem[UserType.CLIENT.getAllTypes().size()];
        int i = 0;
        for (UserType type : UserType.CLIENT.getAllTypes()) {
            items[i++] = new SelectItem(type, decodeUserType(type));
        }
        return items;
    }
    /**
     * @return the container
     */
    public Container getContainer() {
        return container;
    }
    
    public String decodeUserType(UserType type) {
        switch (type) {
            case CLIENT:
                return "Klient";
            case CASHIER:
                return "Kasjer";
            case ADMINISTRATOR:
                return "Administrator";
        }
        return null;
    }

    /**
     * @param container the container to set
     */
    public void setContainer(Container container) {
        this.container = container;
    }
    
    public void updatePassword(ActionEvent event) throws NoSuchAlgorithmException
    {
        User u = userFacade.findById(container.getUserId());
        u.setPassword(container.getPassword());
        userFacade.save(u);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sukces - hasło zostało zmienione", ""));
        resetPasswordForm();
    }
    
    public void deleteUser(ActionEvent event)
    {
        User user = userFacade.findById(container.getUserId());
        if (user != null) {
            Set<Account> accounts = user.getAccounts();
            for (Account account : accounts) {
                if (account != null) {
                    Set<Transaction> trs = account.getTransactions();
                    for (Transaction transaction : trs) {
                        transactionFacade.remove(transaction);
                    }
                    accountFacade.remove(account);
                }
            }
            user.setAccounts(null);
            userFacade.remove(user);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sukces", "Użytkownik "+user.getName()+" został usunięty"));
            getUsers(true);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Błąd", "Nie można było usunąć użytkownika "+user.getName()+"."));
    }
    
    public void resetPasswordForm()
    {
        container.setPassword("");
    }
    
    public void interceptUserId(ActionEvent event)
    {
        container.setUserId((Long)event.getComponent().getAttributes().get("userId"));
    }
}