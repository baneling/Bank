/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.client;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Mateusz
 */
@Stateless
@LocalBean
public class TransactionOutcoming {
    
    public boolean checkAccount(Long accountNumber){
        TransactionsWebservis_Service client = new TransactionsWebservis_Service();
        TransactionsWebservis port = client.getTransactionsWebservisPort();
        return port.checkAccount(accountNumber);
    }
    
    public void send( 
            Long accountNumber,
            String senderName,
            String senderSurname,
            String description,
            Long receverNumber,
            float amount
            ){
        TransactionsWebservis_Service client = new TransactionsWebservis_Service();
        TransactionsWebservis port = client.getPort(TransactionsWebservis.class);
        port.receive(accountNumber, senderName, senderSurname, description, receverNumber, amount);
    }
           
}
