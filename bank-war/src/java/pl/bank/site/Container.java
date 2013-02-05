/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.site;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import pl.bank.entity.User;

/**
 *
 * @author carbolymer
 */
@ManagedBean
@SessionScoped
public class Container implements Serializable{
    private long accountNumber;
    private long userId;
    private String password;
    /**
     * Creates a new instance of Container
     */
    public Container() {
    }

    /**
     * @return the accountNumber
     */
    public long getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
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
}
