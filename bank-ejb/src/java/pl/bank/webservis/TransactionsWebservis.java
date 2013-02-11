/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.webservis;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author carbolymer
 */
@WebService(serviceName = "TransactionsWebservis")
@Stateless()
public class TransactionsWebservis {
    @EJB
    private TransactionsIncoming ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "checkAccount")
    public boolean checkAccount(@WebParam(name = "accountNumber") Long accountNumber) {
        return ejbRef.checkAccount(accountNumber);
    }

    @WebMethod(operationName = "receive")
    @Oneway
    public void receive(@WebParam(name = "accountNumber") Long accountNumber, @WebParam(name = "senderName") String senderName, @WebParam(name = "senderSurname") String senderSurname, @WebParam(name = "description") String description, @WebParam(name = "senderNumber") Long senderNumber, @WebParam(name = "amount") float amount) {
        ejbRef.receive(accountNumber, senderName, senderSurname, description, senderNumber, amount);
    }
    
}
