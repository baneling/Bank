/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.site;

import java.io.IOException;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import pl.bank.entity.Account;
import pl.bank.entity.AccountFacade;
import pl.bank.entity.Transaction;
import pl.bank.entity.TransactionFacade;
import pl.bank.entity.TransactionType;
import pl.bank.entity.User;
import pl.bank.entity.UserFacade;

/**
 *
 * @author carbolymer
 */
@ManagedBean
@SessionScoped
public class NewTransaction {
    @EJB
    private TransactionFacade transactionFacade;
    @EJB
    private AccountFacade accountFacade;
    @EJB
    private UserFacade userFacade;
    
    private Long senderAccountNumber = null;
    private Long accountNumber = null;
    private float amount = 0f;
    private String description = null;
    
    @ManagedProperty("#{authorization}")
    private Authorization authorizationBean;
    /**
     * Creates a new instance of NewTransaction
     */
    public NewTransaction() {
    }

    public void send(ActionEvent event) throws IOException{
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        User currentUser = (User) session.getAttribute("user");
        Account senderAccount = accountFacade.getByNumber(senderAccountNumber);
        Account recipientAccount;
        
        if(!senderAccount.getUser().equals(currentUser))
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Konto nadawcy nie jest kontem użytkownika!", ""));
        }
        else
        {
            // tutaj sprawdzanie numerów konta - uwzględnić webserwis
            recipientAccount = accountFacade.getByNumber(accountNumber);
            if(recipientAccount == null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Konto o takim numerze nie istnieje!", ""));
            }
            else
            {
                if(senderAccount.getBalance() < amount)
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak wystarczających środków na koncie!", ""));
                else
                {
                    transfer(senderAccount,recipientAccount,amount);
                    reset();
                    FacesContext.getCurrentInstance().getExternalContext().redirect("client.xhtml");
                }
            }
            
        }
            
    }
    public void reset()
    {
        setAccountNumber(null);
        setAmount(0f);
        setDescription(null);
//        RequestContext.getCurrentInstance().reset("form:newTransactionGrid");  
    }
    
    public void transfer(Account sender, Account recipient, float amount)
    {
        System.err.println("TRANSFER");
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
        accountFacade.save(sender);
        accountFacade.save(recipient);
        // outgoing
        Transaction transaction = new Transaction();     
        transaction.setAccount(sender);
        transaction.setAmount(amount);
        transaction.setCompletionDate(Calendar.getInstance().getTime());
        transaction.setSecondSide(recipient.getUser().getName()+" "+recipient.getUser().getSurname()+" / "+recipient.getNumber());
        transaction.setDescription(description);
        transaction.setType(TransactionType.OUTGOING);
        transactionFacade.save(transaction);
        
        // incoming
        transaction = new Transaction();     
        transaction.setAccount(recipient);
        transaction.setAmount(amount);
        transaction.setCompletionDate(Calendar.getInstance().getTime());
        transaction.setSecondSide(sender.getUser().getName()+" "+sender.getUser().getSurname()+" / "+sender.getNumber());
        transaction.setDescription(description);
        transaction.setType(TransactionType.INCOMING);
        transactionFacade.save(transaction);

        authorizationBean.setUser(userFacade.find(authorizationBean.getUser().getId()));
    }

    /**
     * @return the accountNumber
     */
    public Long getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the amount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(float amount) {
        this.amount = amount;
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
     * @return the senderAccountNumber
     */
    public Long getSenderAccountNumber() {
        return senderAccountNumber;
    }

    /**
     * @param senderAccountNumber the senderAccountNumber to set
     */
    public void setSenderAccountNumber(Long senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
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
}
