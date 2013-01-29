/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 *
 * @author carbolymer
 */
@Entity
@NamedQueries({
    @NamedQuery(
       name = "getAccounts", 
       query="SELECT a FROM Account a"
    ),
    @NamedQuery(
       name = "findByNumber", 
       query="SELECT a FROM Account a WHERE a.number = :number"
    )
})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long number;
    private float balance;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "account", fetch= FetchType.EAGER)
    @OrderBy("completionDate DESC")    
    private Set<Transaction> transactions = new HashSet<Transaction>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.bank.entity.Konto[ id=" + id + " ]";
    }

    /**
     * @return the balance
     */
    public float getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(float balance) {
        this.balance = balance;
    }

    /**
     * @return the number
     */
    public Long getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the transactions
     */
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<Transaction> getTransactionsList() {
        return new ArrayList<Transaction>(transactions);
    }
    
    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    
}
