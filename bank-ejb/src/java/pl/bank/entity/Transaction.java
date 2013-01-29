/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author carbolymer
 */
@Entity
@Table(name="transactions")
@NamedQueries({
    @NamedQuery(
       name = "getTransactions", 
       query="SELECT t FROM Transaction t"
    )
})
public class Transaction implements Serializable {
    @ManyToOne
    private Account account;
    private float amount;
    private String secondSide;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDate;
    private TransactionType type;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.bank.entity.Transaction[ id=" + id + " ]";
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the amount
     */
    public float getAmount() {
        if(type == TransactionType.INCOMING || amount == 0f)
            return amount;
        else
            return amount*(-1f);
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /**
     * @return the completionDate
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * @return the secondSide
     */
    public String getSecondSide() {
        return secondSide;
    }

    /**
     * @param secondSide the secondSide to set
     */
    public void setSecondSide(String secondSide) {
        this.secondSide = secondSide;
    }

    /**
     * @return the transactionType
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setType(TransactionType transactionType) {
        this.type = transactionType;
    }
    
    public String getRecipient()
    {
        if(type == TransactionType.INCOMING)
            return account.getUser().getName()+" "+account.getUser().getSurname();
        else
            return secondSide;
    }
    
    public String getSender()
    {
        if(type == TransactionType.INCOMING)
            return secondSide;
        else
            return account.getUser().getName()+" "+account.getUser().getSurname();
    }    
}
