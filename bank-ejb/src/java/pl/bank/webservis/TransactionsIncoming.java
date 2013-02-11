/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.webservis;

import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import pl.bank.entity.Account;
import pl.bank.entity.AccountFacade;
import pl.bank.entity.Transaction;
import pl.bank.entity.TransactionFacade;
import pl.bank.entity.TransactionType;

/**
 *
 * @author Mateusz
 */
@Stateless
@LocalBean
public class TransactionsIncoming {
    @EJB
    private TransactionFacade transactionFacade;
    @EJB
    private AccountFacade accountFacade;
    
    public boolean checkAccount(Long accountNumber){
        Account account = accountFacade.getByNumber(accountNumber);
        
        if(account == null){
            return false;
        }
        else{
            return true;
        }
    }
    
    public void receive( 
            Long accountNumber,
            String senderName,
            String senderSurname,
            String description,
            Long senderNumber,
            float amount){
        
        Account recipient = accountFacade.getByNumber(accountNumber);
        
        recipient.setBalance(recipient.getBalance() + amount);
        accountFacade.save(recipient);
        
        Transaction transaction = new Transaction(); 
        
        transaction = new Transaction();     
        transaction.setAccount(recipient);
        transaction.setAmount(amount);
        transaction.setCompletionDate(Calendar.getInstance().getTime());
        transaction.setSecondSide(senderName+" "+senderSurname+" / "+senderNumber);
        transaction.setDescription(description);
        transaction.setType(TransactionType.INCOMING);
        transactionFacade.save(transaction);
    }

}
