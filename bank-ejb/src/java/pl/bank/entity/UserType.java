/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.entity;

import java.util.ArrayList;

/**
 *
 * @author carbolymer
 */
public enum UserType {
    CLIENT,
    CASHIER,
    ADMINISTRATOR;
    public ArrayList<UserType> getAllTypes()
    {
        ArrayList<UserType> types = new ArrayList<UserType>();
        types.add(UserType.CLIENT);
        types.add(UserType.CASHIER);
        types.add(UserType.ADMINISTRATOR);
        return types;
    }
    
    public String getValue()
    {
        if (this == UserType.CLIENT) {
            return "CLIENT";
        }
        if (this == UserType.CASHIER) {
            return "CASHIER";
        }
        if (this == UserType.ADMINISTRATOR) {
            return "ADMINISTRATOR";
        }
        return null;
    }
}