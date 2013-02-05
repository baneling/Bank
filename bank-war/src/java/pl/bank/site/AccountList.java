/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.site;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import pl.bank.entity.Account;
import pl.bank.entity.AccountFacade;

/**
 *
 * @author carbolymer
 */
@ManagedBean
@RequestScoped
public class AccountList {
    @EJB
    private AccountFacade accountFacade;
    private List<Account> accounts = new ArrayList<Account>();
    private List<Account> filteredAccounts = new ArrayList<Account>();
    /**
     * Creates a new instance of AccountList
     */
    public AccountList() {
    }

    /**
     * @return the accountFacade
     */
    public AccountFacade getAccountFacade() {
        return accountFacade;
    }

    /**
     * @param accountFacade the accountFacade to set
     */
    public void setAccountFacade(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    public void refreshAccounts()
    {
        accounts = accountFacade.getAll();
        filteredAccounts = accounts;
    }
    
    /**
     * @return the accounts
     */
    public List<Account> getAccounts() {
        if(accounts.size() == 0)
            refreshAccounts();
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * @return the filteredAccounts
     */
    public List<Account> getFilteredAccounts() {        
        return filteredAccounts;
    }

    /**
     * @param filteredAccounts the filteredAccounts to set
     */
    public void setFilteredAccounts(List<Account> filteredAccounts) {
        this.filteredAccounts = filteredAccounts;
    }
}
